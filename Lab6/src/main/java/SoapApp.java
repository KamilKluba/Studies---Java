
import org.w3c.dom.DOMException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class SoapApp {
    private final String LOG_RECEIVE = "Otrzymano";
    private final String LOG_TO_ME = "Do mnie";
    private final String LOG_FORWARD = "Przekazano";
    private final String LOG_DROP = "Upuszczono";

    private final String BODY_MESSAGE = "Message";
    private final String HEADER_NAMESPACE_URI = "head";
    private final String HEADER_SENDER = "Sender";
    private final String HEADER_RECEIVER = "ReceiverUltimate";
    private final String HEADER_PATH_NODES = "PathNodes";
    private final String HEADER_PATH_NODE = "PathNode";

    private SoapAppController controller;
    private String nextNodeHost = "localhost";
    private int nextNodePort;
    private int port;
    private String nodeIdentifier;
    private ServerSocket serverSocket;
    private Thread serverThread;
    private volatile boolean threadRunning = true;

    public SoapApp(int port, String nodeIdentifier, int nextNodePort) {
        this.port = port;
        this.nodeIdentifier = nodeIdentifier;
        this.nextNodePort = nextNodePort;
    }

    public void startListening() {
        final ExecutorService executorService = Executors.newFixedThreadPool(10);

        serverThread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                while (threadRunning) {
                    Socket clientSocket = serverSocket.accept();
                    executorService.submit(() -> {
                        try {
                            SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null,
                                    clientSocket.getInputStream());
                            onSoapMessageReceived(soapMessage);
                            clientSocket.close();
                        } catch (IOException | SOAPException e) {
                        }

                    });
                }
            } catch (IOException e) { } finally {
                executorService.shutdown();
            }
        });

        serverThread.start();
    }

    public void stopListening() throws IOException {
        serverSocket.close();
        threadRunning = false;
    }

    public void sendMessage(String receiver, String message) throws SOAPException, UnknownHostException, IOException {
        SOAPMessage soapMessage = createEnvelope(nodeIdentifier, receiver, message);

        onSoapMessageReadyToSend(soapMessage);
    }

    public void sendMessage(SOAPMessage soapMessage) throws SOAPException, UnknownHostException, IOException {
        onSoapMessageReadyToSend(soapMessage);
    }

    protected void forwardTo(SOAPMessage soapMessage, String host, int port) throws SOAPException, IOException {
        Socket socket = new Socket(host, port);
        soapMessage.writeTo(socket.getOutputStream());
        socket.getOutputStream().flush();
        socket.close();
    }

    protected void onSoapMessageReceived(SOAPMessage soapMessage) {
        controller.log(LOG_RECEIVE);

        try {
            String receiver = extractReceiver(soapMessage);

            if(extractPathNodes(soapMessage).contains(nodeIdentifier)) {
                controller.log(LOG_DROP);
                return;
            }

            if (receiver.equals(nodeIdentifier) || receiver.equals("*")){
                controller.log(LOG_TO_ME);
                String sender = extractSender(soapMessage);
                String message = extractMessage(soapMessage);
                controller.showReceivedMessage(sender, message);
            }

            if (!receiver.equals(nodeIdentifier)) {
                List<String> pathNodes = extractPathNodes(soapMessage);

                if (!pathNodes.contains(nodeIdentifier)) {
                    addPathNode(soapMessage, nodeIdentifier);

                    controller.log(LOG_FORWARD);
                    sendMessage(soapMessage);
                } else {
                    controller.log(LOG_DROP);
                }
            } else {
                controller.log(LOG_DROP);
            }

        } catch (SOAPException | IOException e) {
            controller.showError("Błąd!", e.getMessage());
        }
    }

    protected void onSoapMessageReadyToSend(SOAPMessage soapMessage) {
        try {
            addPathNode(soapMessage, nodeIdentifier);
            forwardTo(soapMessage, nextNodeHost, nextNodePort);
        } catch (IOException | SOAPException e) {
            controller.showError("Error", e.getMessage());
        }
    }

    public SOAPMessage createEnvelope(String sender, String receiver, String message) throws DOMException, SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();

        SOAPMessage soapMessage = messageFactory.createMessage();

        soapMessage.getSOAPHeader().addChildElement(new QName(HEADER_NAMESPACE_URI, HEADER_SENDER)).setTextContent(sender);
        soapMessage.getSOAPHeader().addChildElement(new QName(HEADER_NAMESPACE_URI, HEADER_RECEIVER)).setTextContent(receiver);
        soapMessage.getSOAPHeader().addChildElement(new QName(HEADER_NAMESPACE_URI, HEADER_PATH_NODES));

        soapMessage.getSOAPBody().addBodyElement(new QName(BODY_MESSAGE)).setTextContent(message);

        return soapMessage;
    }

    public String extractSender(SOAPMessage soapMessage) throws SOAPException {
        return (((SOAPElement)soapMessage.getSOAPHeader().getChildElements(new QName(HEADER_NAMESPACE_URI, HEADER_SENDER)).next()).getTextContent());
    }

    public String extractReceiver(SOAPMessage soapMessage) throws SOAPException {
        return (((SOAPElement)soapMessage.getSOAPHeader().getChildElements(new QName(HEADER_NAMESPACE_URI, HEADER_RECEIVER)).next()).getTextContent());
    }

    public List<String> extractPathNodes(SOAPMessage soapMessage) throws SOAPException {
        List<String> pathNodes = new ArrayList<>();

        SOAPElement pathNodesElement = (SOAPElement) soapMessage.getSOAPHeader().getChildElements(new QName(HEADER_NAMESPACE_URI, HEADER_PATH_NODES)).next();

        Iterator<SOAPElement> pathNodeElements = pathNodesElement.getChildElements(new QName(HEADER_NAMESPACE_URI, HEADER_PATH_NODE));

        while(pathNodeElements.hasNext()) {
            pathNodes.add(pathNodeElements.next().getTextContent());
        }

        return pathNodes;
    }

    public SOAPMessage addPathNode(SOAPMessage soapMessage, String pathNode) throws SOAPException {
        SOAPElement pathNodesElement = (SOAPElement) soapMessage.getSOAPHeader().getChildElements(new QName(HEADER_NAMESPACE_URI, HEADER_PATH_NODES)).next();

        pathNodesElement.addChildElement(new QName(HEADER_NAMESPACE_URI, HEADER_PATH_NODE)).setTextContent(pathNode);

        return soapMessage;
    }

    public String extractMessage(SOAPMessage soapMessage) throws SOAPException {
        return ((SOAPElement)soapMessage.getSOAPBody().getChildElements(new QName(BODY_MESSAGE)).next()).getTextContent();
    }

    public int getNextNodePort() {
        return nextNodePort;
    }

    public void setController(SoapAppController controller) {
        this.controller = controller;
    }

    public String getNetNodeHost() {
        return nextNodeHost;
    }

    public int getPort() {
        return port;
    }

    public String getNodeIdentifier() {
        return nodeIdentifier;
    }
}
