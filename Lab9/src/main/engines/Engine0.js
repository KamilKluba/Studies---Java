function makeNextMove(gameBoard,symbol){

    var min=0;
    var max=15;
    var randomPosition = 0;

    do{
        randomPosition = Math.floor(Math.random() * (+max - +min)) + +min;
    }while(gameBoard[randomPosition] === ' ');

    gameBoard[randomPosition] = symbol;
    return randomPosition;
}