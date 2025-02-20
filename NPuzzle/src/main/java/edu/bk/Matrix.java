package edu.bk;


public class Matrix {
    private int[][] value;
    private int sizebox;
    private int countmove;
    private int mtdistance;
    private int x_axis;
    private int y_axis;
    private int Astar;
    private int Bstar;
    private int step = 0;
//    private int linearConflictHeuristic;
    private String closedMatrix = "";
    private String moveDirection;
    private String parentString = "";
    
    // Do sau cua node
    private int depth = 0;
    
    public static int[] indexRows = new int[100];
    public static int[] indexCols = new int[100];
    
    public Matrix(){

    }
    public Matrix(int[][] value, int sizebox) {
        this.sizebox = sizebox;
        int sizes = sizebox * sizebox;
        this.countmove = 0;
        this.moveDirection = "";
        this.value = new int[sizebox + 1][sizebox + 1];
        for(int i = 0; i < sizebox; i++) {
            for (int j = 1; j <= sizebox; j++) {
                this.value[i][j] = value[i][j];
                if (value[i][j] == sizes) {
                    this.x_axis = j;
                    this.y_axis = i;
                }
            }
        }
        for(int i = 1; i <= sizes; i++){
            if(i % sizebox == 0){
                indexCols[i] = sizebox;
                indexRows[i] = i / sizebox - 1;
            } else {
                indexCols[i] = i % sizebox;
                indexRows[i] = i / sizebox;
            }
        }
        countdistance();
        countdistance2();
        Stringg();
        this.parentString = getClosedMatrix();
        
//        this.display();
    }
    public void isParentMoveLeft(Matrix p){
        this.sizebox = p.getSizebox();
        this.value = new int[this.sizebox + 1][this.sizebox + 1];
        this.countmove = p.getCountmove() + 1;
        int sizes = this.sizebox*this.sizebox;
        int[][] tmp = p.getValue();
        for(int i = 0; i < sizebox; i++){
            for(int j = 1; j <= sizebox; j++){
                this.value[i][j] = tmp[i][j];
                if(tmp[i][j] == sizes){
                    this.value[i][j] = tmp[i][j-1];
                    this.value[i][j-1] = sizes;
                    this.y_axis = i;
                    this.x_axis = j - 1;
                }
            }
        }
        this.moveDirection = p.getMoveDirection().concat("left-");
        countdistance();
        countdistance2();
        Stringg();
        this.parentString = p.getParentString().concat("-" + getClosedMatrix());
    }
    public void isParentMoveRight(Matrix p){
        this.sizebox = p.getSizebox();
        this.value = new int[this.sizebox + 1][this.sizebox + 1];
        this.countmove = p.getCountmove() + 1;
        int sizes = this.sizebox*this.sizebox;
        int[][] tmp = p.getValue();
        for(int i = 0; i < sizebox; i++){
            for(int j = sizebox; j > 0; j--){
                this.value[i][j] = tmp[i][j];
                if(tmp[i][j] == sizes){
                    this.value[i][j] = tmp[i][j+1];
                    this.value[i][j+1] = sizes;
                    this.x_axis = j + 1;
                    this.y_axis = i;
                }
            }
        }
        this.moveDirection = p.getMoveDirection().concat("right-");
        countdistance();
        countdistance2();
        Stringg();
        this.parentString = p.getParentString().concat("-" + getClosedMatrix());
    }
    public void isParentMoveTop(Matrix p){
        this.sizebox = p.getSizebox();
        this.value = new int[this.sizebox + 1][this.sizebox + 1];
        this.countmove = p.getCountmove() + 1;
        int sizes = this.sizebox*this.sizebox;
        int[][] tmp = p.getValue();
        for(int i = 0; i < sizebox; i++){
            for(int j = sizebox; j > 0; j--){
                this.value[i][j] = tmp[i][j];
                if(tmp[i][j] == sizes){
                    this.value[i][j] = tmp[i-1][j];
                    this.value[i-1][j] = sizes;
                    this.x_axis = j;
                    this.y_axis = i - 1;
                }
            }
        }
        this.moveDirection = p.getMoveDirection().concat("top-");
        countdistance();
        countdistance2();
        Stringg();
        this.parentString = p.getParentString().concat("-" + getClosedMatrix());
    }
    public void isParentMoveBot(Matrix p){
        this.sizebox = p.getSizebox();
        this.value = new int[this.sizebox + 1][this.sizebox + 1];
        this.countmove = p.getCountmove() + 1;
        int sizes = this.sizebox*this.sizebox;
        int[][] tmp = p.getValue();
        for(int i = sizebox - 1; i > -1; i--){
            for(int j = sizebox; j > 0; j--){
                this.value[i][j] = tmp[i][j];
                if(tmp[i][j] == sizes){
                    this.value[i][j] = tmp[i+1][j];
                    this.value[i+1][j] = sizes;
                    this.x_axis =  j;
                    this.y_axis = i + 1;
                }
            }
        }
        this.moveDirection = p.getMoveDirection().concat("bot-");
        countdistance();
        countdistance2();
        Stringg();
        this.parentString = p.getParentString().concat("-" + getClosedMatrix());
    }
    private void Stringg(){
        for(int i = 0; i < sizebox; i++){
            for(int j = 1; j <= sizebox; j++){
                this.closedMatrix = this.closedMatrix.concat(String.valueOf(this.value[i][j]));
            }
        }
    }
    private void countdistance(){
        int distance = 0;
        for(int i = 0; i < sizebox; i++){
            for(int j = sizebox; j > 0; j--){
                int tmp = value[i][j];
                distance = Math.abs(indexCols[tmp] - j) + Math.abs(indexRows[tmp] - i) + distance;
            }
        }
        this.mtdistance = distance;
        this.Astar = this.mtdistance + this.countmove;
    } // đếm khoảng cách hàm h

    private void countdistance2(){
        int xx = 0, yy = 0;
        for(int i = 0; i < sizebox; i++){
            for(int j = sizebox; j > 0; j--){
            	if(value[i][j] != i * sizebox + j) {
            		step  = i;
            		break;
            	}
            }
            if(step != 0) {
            	break;
            }
        }
        for(int i = 0; i < sizebox; i++){
            for(int j = sizebox; j > 0; j--){
            	if(value[i][j] == sizebox * sizebox) {
            		xx = i;
            		yy = j;
            		break;
            	}
            }
        }
        int distance = 0;
        for(int i = 0; i < sizebox; i++){
            for(int j = sizebox; j > 0; j--){
            	for(int k = sizebox; k > 0; k--) {
            		if(value[i][j] == step * sizebox + k) {
            			distance += Math.abs(i - step) + Math.abs(i - xx) + Math.abs(j - yy) + Math.abs(j - k);
            		}
            	}
            	if(xx - 1 == step) {
            		if(value[step][j] != step * sizebox + j) {
            			distance--;
            		}
            	}
            }
        }
//        System.out.println(distance);
        this.Bstar = distance + Astar;
    } // đếm khoảng cách hàm h
    
    public int[][] getValue() {
        return value;
    }
    public boolean moveLeft(){
        return x_axis > 1;
    }
    public boolean moveRight(){
        return x_axis < sizebox;
    }
    public boolean moveTop(){
        return y_axis > 0;
    }
    public boolean moveBot(){
        return y_axis < sizebox - 1;
    }

    public int getSizebox() {
        return sizebox;
    }

    public int getCountmove() {
        return countmove;
    }

    public int getMtdistance() {
        return mtdistance;
    }
    public int getX_axis() {
        return x_axis;
    }

    public String getMoveDirection() {
        return moveDirection;
    }

    public int getY_axis() {
        return y_axis;
    }

    public String getParentString() {
        return parentString;
    }

    public void display(){
        for(int i = 0; i < sizebox; i++){
            for(int j = 1; j <= sizebox; j++){
                System.out.print(value[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    public int getAstar() {
        return Astar;
    }

    public String getClosedMatrix() {
        return closedMatrix;
    }
    
    public void setDepth(int depth) {
    	this.depth = depth;
    }
    
    public int getDepth() {
    	return this.depth;
    }
	public int getBstar() {
		return Bstar;
	}
	public void setBstar(int bstar) {
		Bstar = bstar;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
    
    
}
