package main_program;

public class Player {
    private int player_number;
    private int bridge_card;
    private int philips_driver;
    private int hammer;
    private int saw;
    private int score;
    private int rank;
    private int row;
    private int col;

    public Player(int number){
        this.player_number = number;
        this.bridge_card = 0;
        this.philips_driver = 0;
        this.hammer = 0;
        this.saw = 0;
        this.score = 0;
        this.rank = 0;
        this.row = 0;
        this.col = 0;
        //this.position = 0;
    }
    public void setPosition(int r, int c){
        this.row = r;
        this.col = c;
    }
    public void setToolCard(int pd, int ham, int s){
        this.philips_driver += pd;
        this.hammer += ham;
        this.saw += s;
    }
    public void setBridgeCard(int b){
        this.bridge_card += b;
        if (this.bridge_card < 0){
            this.bridge_card = 0;
        }
    }
    public void setRank(int r){
        this.rank = r;
    }
    public int getScore(){
        int end_score = 0;
        if (rank==1){
            end_score = 7;
        }
        else if (rank == 2){
            end_score = 3;
        }
        else if (rank == 3){
            end_score = 1;
        }
        this.score = end_score + this.philips_driver + this.hammer * 2 + this.saw * 3;
        return this.score;
    }
    public int getBridgeCard(){ return this.bridge_card;}
    public int getPDCard(){ return this.philips_driver;}
    public int getSawCard(){ return this.saw;}
    public int getHammerCard(){ return this.hammer;}
    public int getRank(){ return this.rank;}
    public int getRow(){ return this.row;}
    public int getCol(){ return this.col;}
    public int getPlayerNumber() { return  this.player_number;}
}
