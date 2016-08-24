import java.util.Random;
import java.util.Scanner;
/**
* This is task 8
* 
* Create a digital version of the popular board game known as battleships
*
* @author  ABDUL SAIF
* @version 1.7
* @since   19/08/2016
*/
public class Task8 {
	
	public static void main(String[] args){
        int boardSize = 5; 										// INITIALISE 'boardSize'
        int counter = 0;										// INITIALISE game 'counter'
        int lives = 0;											// INITIALISE 'lives'
        int player = 0;											// INITIALISE 'player'
        
        int[][] boardOne = new int[boardSize][boardSize]; 		// CREATE Multi-dimensional array for the battleship board
        int[][] boardTwo = new int[boardSize][boardSize]; 		// CREATE Multi-dimensional array for the battleship board
        int[][] shipsOne = new int[boardSize][boardSize]; 		// CREATE ships to be placed
        int[][] shipsTwo = new int[boardSize][boardSize]; 		// CREATE ships to be placed  

        
        setEmptyBoard(boardOne);
        setEmptyBoard(boardTwo);
        coordShips(shipsOne, boardOne, boardTwo, player);
        coordShips(shipsTwo, boardOne, boardTwo, player);
//        placedShips(shipsOne, row, column, lives); 				// RANDOMLY placed ships
//        placedShips(shipsTwo, row, column, lives); 				// RANDOMLY placed ships
        welcome();												// CALL 'welcome' Method
		playerTurn(counter, boardOne, boardTwo, shipsOne, shipsTwo, lives);
		
	} // END of main CLASS
	
    public static void coordShips(int[][] board, int[][] boardOne, int[][]boardTwo, int player){
    	int[] ships = new int [5];
    	int battleship = 4, carrier = 5, cruiser = 3, patrol = 2, submarine = 3;
    	
    	ships[0] = battleship; ships[1] = carrier; ships[2] = cruiser; ships[3] = patrol; ships[4] = submarine;
    	
    	System.out.println("\nYou are requried to place a total of 5 ships onto the board\n");
    	
    	for(int b = 0; b < board.length-1; b++)
    	{
    		System.out.println("\n\nShip " + (b+1) + ", takes up " + ships[b] + " spaces\n");
    		Scanner column = new Scanner(System.in);					//  Take INPUT from user for column
    		int columnInput;
       
        do {
            System.out.print("Please enter your column: ");             //  Take column
            while (!column.hasNextInt()) {                              //  WHILE column is not occupied, take input
                column.next();											//  CHECK if the scanner's next token is an integer
            }
            columnInput = column.nextInt();
        } while (columnInput > board.length-1 || columnInput < 1);    	//  LOOP until a valid integer is entered
 
        Scanner row = new Scanner(System.in);							//  Take INPUT from user for column
        int rowInput;
       
        do {
            System.out.print("Please enter your row: ");                //  Take row
            while (!row.hasNextInt()) {                                 //  WHILE column is not occupied, take input
                row.next();												//  CHECK if the scanner's next token is an integer
            }
            rowInput = row.nextInt();
        } while (rowInput > board.length-1 || rowInput < 1);			//  LOOP until a valid integer is entered 
        
        ships2Board(boardOne, boardTwo, column, row, columnInput, rowInput, player);
    }
    }
	
	 //------------------------------------------------------shoot-----------------------------------------------------------
    public static void fire(int column, int row, int player, int[][] boardOne, int[][] boardTwo, int[][] shipsOne, int[][] shipsTwo, int lives){ 
    	if(player % 2 == 0)																//  IF turn counter is divisible by 2, it's player 2's turn
    	{                                                  							    
    		if(shipsOne[row-1][column-1] == 1) {                               		    //  IF shot is a hit
    			boardOne[row-1][column-1] = 2;                                          //  UPDATE display board with hit
    			System.out.println("It's a hit!" + "\n");                               //  CHECK if game is over
    			showBoard(boardOne);
    			System.out.println("\n" + "You get to go again!"); 
    			lives--;
    			playerTurn(player, boardOne, boardTwo, shipsOne, shipsTwo, lives);      //  REPEAT turn
    			}
    		else {                                                                      //  IF shot is a miss
    			boardOne[row-1][column-1] = 0;                                          //  UPDATE display board with miss
    			System.out.println("Unlucky, you missed!" + "\n");
    			showBoard(boardOne);
    			player++;                                                               //  INCREMENT player counter by 1
    			playerTurn(player, boardOne, boardTwo, shipsOne, shipsTwo, lives);      //  Next turn
    			}
    		}
    	else
    	{                                                                 	 	    	//  IF it's player 1's turn
    		if (shipsTwo[row-1][column-1] == 1) {                                       //  IF shot is a hit
    			boardTwo[row-1][column-1] = 2;                                          //  UPDATE display board with hit
    			System.out.println("It's a hit!" + "\n");                                                  //  Check if game is over
    			showBoard(boardTwo);
    			System.out.println("\n" + "You get to go again!");
    			lives++;
    			playerTurn(player, boardOne, boardTwo, shipsOne, shipsTwo, lives);     	//  REPEAT turn
    			}
    		else {                                                        	    	    //  IF shot is a miss
    			boardTwo[row-1][column-1] = 0;                                  	    //  UPDATE display board with miss
    			System.out.println("Unlucky, you missed!" + "\n");
    			showBoard(boardTwo);
       			player++;                                                      	 	    //  INCREMENT player counter by 1
       			playerTurn(player, boardOne, boardTwo, shipsOne, shipsTwo, lives); 	    //  Next turn
       			}
    		}
    	}
    
    //---------------------------------------------------placedShips--------------------------------------------------------
    
    public static void placedShips(int[][] ships, int row, int column, int lives){ // REDUNDANT - METHOD GOOD FOR RANDOM
        Random random = new Random(); // GENERATE random number
        
        for(int ship = 0 ; ship < 3 ; ship++){
            ships[ship][0] = random.nextInt(4);
            ships[ship][1] = random.nextInt(4);
            
            //let's check if that shot was already tried 
            //if it was, just finish the do...while when a new pair was randomly selected
            for(int last = 0 ; last < ship ; last++){
                if( (ships[ship][0] == ships[last][0])&&(ships[ship][1] == ships[last][1]) )
                    do{
                        ships[ship][0] = random.nextInt(4);
                        ships[ship][1] = random.nextInt(4);
                        lives++;
                    }while( (ships[ship][0] == ships[last][0])&&(ships[ship][1] == ships[last][1]) );
            }     
        }
    }
    
    //----------------------------------------------------playerTurn---------------------------------------------------------
    public static void playerTurn(int player, int[][] boardOne, int[][] boardTwo, int[][]shipsOne, int[][]shipsTwo, int lives){     
    	
    	turnHint(player);												// CALL 'turnHint' Method
       
        Scanner column = new Scanner(System.in);
        int columnInput;
       
        do {
            System.out.print("Please enter your column: ");           //  Take column
            while (!column.hasNextInt()) {                              
                column.next();											//  CHECK if the scanner's next token is an integer
            }
            columnInput = column.nextInt();
        } while (columnInput > boardOne.length || columnInput < 1);		//  LOOP until a valid integer is entered
 
        Scanner row = new Scanner(System.in);
        int rowInput;
       
        do {
            System.out.print("Please enter your row: ");              //  Take row
            while (!row.hasNextInt()) {                                 
                row.next();												//  CHECK if the scanner's next token is an integer
            }
            rowInput = row.nextInt();
        } while (rowInput > boardOne.length || rowInput < 1);			//  LOOP until a valid integer is entered
       
        fire(columnInput, rowInput, player, boardOne, boardTwo, shipsOne, shipsTwo, lives); // CALL 'fire' Method
    }
    
	//---------------------------------------------------setEmptyBoard-------------------------------------------------------
    public static void setEmptyBoard(int[][] board){                // Function to create game board
        for(int row=0; row < board.length; row++)                   // Go through every row
            for(int column=0; column < board.length; column++) 		// Go through every column
                    board[row][column]=-1;							// SET all blocks to -1
    }
    
	//----------------------------------------------------ships2Board--------------------------------------------------------
    public static void ships2Board(int[][] boardOne, int[][] boardTwo, int column, int row, int columnInput, int rowInput, int player){
    	if(player % 2 == 0)													//  IF turn counter is divisible by 2 with remainder it is Player 1's turn
    	{     
        for(int a = (row); a <= (row+(rowInput-1)); a++){					//  CHECK row length is within the maximum row length
        	if(boardOne[a-1][column-1] == 1){								//  IF row and column equal 1 = boats exists
        		System.out.println("Ships exists here\nTRY AGAIN!");
        		}
        	else{
                    boardOne[a-1][column-1] = 1;
                }
        	}
        for(int b = (column); b <= (column+(columnInput-1)); b++){
        if(boardOne[row-1][b-1] == 1){
            System.out.println("Ships exists here\nTRY AGAIN!");
            }
        else{
                boardOne[row-1][b-1] = 1;
            }
        }
        showBoard(boardOne);
    	}
    	else{															  //  Player 2's turn
            for(int c=(row); c<=(row+(rowInput-1)); c++){				  //  CHECK row length is within the maximum row length
            	if(boardTwo[c-1][column-1] == 1){						  //  IF row and column equal 1 = boats exists
            		System.out.println("Ships exists here\nTRY AGAIN!");
            		}
            	else{
                        boardTwo[c-1][column-1] = 1;
                    }
            	}
            for(int d = (column); d <= (column+(columnInput-1)); d++){
            if(boardTwo[row-1][d-1] == 1){
                System.out.println("Ships exists here\nTRY AGAIN!");
                }
            else{
                    boardTwo[row-1][d-1] = 1;
                }
            }
            showBoard(boardTwo);
    	}
    }	
    
    //---------------------------------------------------showBoard----------------------------------------------------------
    public static void showBoard(int[][] board){
        System.out.println(" \t1 \t2 \t3 \t4 \t5\n");
       
        for(int row=0 ; row < board.length ; row++ ){
            System.out.print((row+1)+"");
            for(int column=0 ; column < board.length ; column++ ){
                if(board[row][column] == -1){
                    System.out.print("\t"+"~~~"); 					// Empty board, not hit
                }else if(board[row][column] == 0){
                    System.out.print("\t"+" X"); 					// When shot is fired, missed
                }else if(board[row][column] == 1){	
                    System.out.print("\t"+" B"); 					// Shot successfully fired, hit MAKE SWIGGLE and make B somewhere else
                }
            } System.out.println();
        }
    }
    
    //---------------------------------------------------turnHint-----------------------------------------------------------
    public static void turnHint(int player){
        if(player % 2 == 0) {                                       //  If turn counter is divisible by 2, it's player 2's turn
            System.out.println("\n\n\n\nPlayer 1's turn!");
        }
        else {
                System.out.println("\n\n\n\nPlayer 2's turn!");
        }
    }
    
	//-----------------------------------------------------welcome----------------------------------------------------------
    private static void welcome(){
        System.out.println("\n\tBATTLESHIPS - THE GAME\n\tWelcome, Let's get started!");
	}

       
	} // END of public CLASS TASK8

//http://www.dreamincode.net/forums/topic/291443-battleship-placing-ships-on-board/
//https://www.daniweb.com/programming/software-development/threads/387845/battleship-help-pretty-challenging - Play with AI