package main_program;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class MainGame {
    private GameManager p;
    private String [][]board;
    private int board_row;
    private int board_col;
    private int player_number;
    private Vector players;

    public Scanner sc = new Scanner(System.in);
    public MainGame() throws IOException{
        System.out.println("==================GAME START===================");
        System.out.print("플레이어 수를 입력하세요: ");
        this.player_number = sc.nextInt();
        p = new GameManager(this.player_number);
        this.board = p.getBoard();
        this.board_col = p.getCol();
        this.board_row = p.getRow();
        this.players = p.getPlayers();
        this.PrintBoard();
        while (!p.CheckEnd()) {
            for (int i = 0; i < this.player_number; i++) {
                if (p.CheckEnd()){
                    break;
                }
                this.PlayerTurn(i);
                this.PrintBoard();
            }
        }
        this.Winner();
    }

    public void PrintBoard(){
        this.board = p.getBoard();
        int unit_size = this.player_number+2;
        for (int i = 0; i < (this.board_row * 2 + 1); i++){
            for (int j = 0; j < (this.board_col * unit_size+1); j++){
                if (i%2 == 0 && j%unit_size != 0) { // 상하 나누는 벽 그리기
                    if (i == 0) {
                        if (this.board[i / 2][j / unit_size].charAt(0) != 'o') {
                            System.out.print("-");
                        } else {
                            System.out.print(" ");
                        }
                    } else if (i == this.board_row * 2) {
                        if (this.board[i / 2 - 1][j / unit_size].charAt(0) != 'o') {
                            System.out.print("-");
                        } else {
                            System.out.print(" ");
                        }
                    } else {
                        if (this.board[i / 2][j / unit_size].charAt(0) != 'o' || this.board[i / 2 - 1][j / unit_size].charAt(0) != 'o') {
                            System.out.print("-");
                        } else {
                            System.out.print(" ");
                        }
                    }
                }
                else if (j%unit_size == 0 && i%2 == 1){ // 좌우 나누는 벽 그리기.
                    if (j == 0){
                        if (this.board[i/2][j / unit_size].charAt(0) != 'o') {
                            System.out.print("|");
                        }
                        else{
                            System.out.print(" ");
                        }
                    }
                    else if (j == this.board_col*unit_size){
                        if (this.board[i/2][j / unit_size - 1].charAt(0) != 'o') {
                            System.out.print("|");
                        }
                        else{
                            System.out.print(" ");
                        }
                    }
                    else{
                        if (this.board[i/2][j / unit_size].charAt(0) != 'o' || this.board[i/2][j / unit_size - 1].charAt(0) != 'o') {
                            System.out.print("|");
                        }
                        else{
                            System.out.print(" ");
                        }
                    }
                }
                else if (j%unit_size == 0 && i%2 == 0){ // 모서리 부분 그리기
                    if (j == 0 && i != this.board_row*2){
                        if (this.board[i/2][j / unit_size].charAt(0) != 'o') {
                            System.out.print("+");
                        }
                        else if (i != 0 && this.board[i/2-1][j/unit_size].charAt(0) != 'o'){
                            System.out.print("+");
                        }
                        else{
                            System.out.print(" ");
                        }
                    }

                    else if (j == this.board_col*unit_size && i != this.board_row*2){
                        if (this.board[i/2][j / unit_size - 1].charAt(0) != 'o') {
                            System.out.print("+");
                        }
                        else if (i != 0 && this.board[i/2-1][j/unit_size-1].charAt(0) != 'o'){
                            System.out.print("+");
                        }
                        else{
                            System.out.print(" ");
                        }
                    }
                    else if (i == this.board_row*2 && j != this.board_col*unit_size){
                        if (this.board[i/2-1][j / unit_size].charAt(0) != 'o') {
                            System.out.print("+");
                        }
                        else if (j != 0 && this.board[i/2-1][j/unit_size-1].charAt(0) != 'o'){
                            System.out.print("+");
                        }
                        else if (this.board[i/2-1][j/unit_size].charAt(0) != 'o'){
                            System.out.print("+");
                        }
                        else{
                            System.out.print(" ");
                        }
                    }
                    else if (i == this.board_row*2 && j == this.board_col*unit_size){
                        if (this.board[i/2-1][j / unit_size-1].charAt(0) != 'o') {
                            System.out.print("+");
                        }
                        else{
                            System.out.print(" ");
                        }
                    }
                    else{
                        if (this.board[i/2][j / unit_size].charAt(0) != 'o' || this.board[i/2][j / unit_size - 1].charAt(0) != 'o') {
                            System.out.print("+");
                        }
                        else if (i != 0 && this.board[i/2-1][j/unit_size-1].charAt(0) != 'o'){
                            System.out.print("+");
                        }
                        else if (i != 0 && this.board[i/2-1][j/unit_size].charAt(0) != 'o'){
                            System.out.print("+");
                        }
                        else{
                            System.out.print(" ");
                        }
                    }
                }
                else if (j%unit_size == 1 && i%2 == 1){ // 지도 부분 그리기.
                    if (this.board[i/2][j/unit_size].charAt(0) == 'F'){
                        System.out.print("►");
                    }
                    else if (this.board[i/2][j/unit_size].charAt(0) == 'C'){
                        System.out.print(" ");
                    }
                    else if (j != 1 && this.board[i/2][j/unit_size-1].charAt(0) == 'B'){
                        System.out.print("=");
                    }
                    else if (this.board[i/2][j / unit_size].charAt(0) != 'o' && this.board[i/2][j/unit_size].charAt(0) != 'C') {
                        System.out.print(this.board[i/2][j/unit_size].charAt(0));
                    }
                    else{
                        System.out.print(" ");
                    }
                }
                else{
                    if (j/unit_size > 0 && this.board[i/2][j/unit_size-1].charAt(0) == 'B'){
                        System.out.print("=");
                    }
                    else if (this.board[i/2][j/unit_size].length() > 1){
                        if (j%unit_size == 3) {
                            int k;
                            for (k = 1; k < this.board[i / 2][j / unit_size].length(); k++) {
                                System.out.print(this.board[i / 2][j / unit_size].charAt(k));
                            }
                            for (int h = k; h < unit_size-1; h++){
                                System.out.print(" ");
                            }
                        }
                    }
                    else {
                        System.out.print(" ");
                    }
                }
            }
            System.out.print("\n");
        }
        this.players = p.getPlayers();
        for (int k = 0; k < this.player_number; k++){
            Player cur = (Player) this.players.get(k);
            System.out.println("Player " + cur.getPlayerNumber() + " [Philips Driver: " + cur.getPDCard() + ", Hammer: " + cur.getHammerCard() +
                    ", Saw: " + cur.getSawCard() + ", Bridge: " + cur.getBridgeCard() + "]");
        }
    }
    public void PlayerTurn(int i){
        this.players = p.getPlayers();
        Player cur = (Player) this.players.get(i);
        if (cur.getRank() != 0){
            return;
        }
        System.out.printf("\nPlayer %d의 차례 입니다.\n",i + 1);
        while (true) {
            System.out.print("주사위를 굴리시겠습니까? (Y/n) ");
            char ans = sc.next().charAt(0);
            sc.nextLine();
            if (ans == 'Y') {
                int dice = this.getDiceNumber();
                int bridge = cur.getBridgeCard();
                int go = dice - bridge;
                if (go < 0) {
                    go = 0;
                }
                String path;
                if (go != 0) {
                    while (true) {
                        System.out.printf("\n%d칸을 이동할 수 있습니다. (주사위: %d, 다리 카드 수: %d)\n", go, dice, bridge);
                        System.out.println("갈 수 있는 칸 만큼 U,D,L,R 혹은 u,d,l,r 을 조합하여 경로를 입력하세요.");
                        if (p.CheckEndPlayer()){
                            System.out.println("결승선에 도착한 Player가 있으므로 뒤로 이동할 수 없습니다.");
                        }
                        path = sc.nextLine();
                        if (!p.CheckMove(cur, path, go)) {
                            System.out.println("형식이 잘못되었습니다.");
                            continue;
                        }
                        break;
                    }
                }
                else{
                    System.out.printf("\n주사위에서 다리 카드를 제외하여 이동할 수 없습니다. (주사위: %d, 다리 카드 수: %d)\n", dice, bridge);
                    break;
                }
                p.PlayerMove(cur, path);
                break;
            } else if (ans == 'n') {
                cur.setBridgeCard(-1);
                System.out.printf("Player %d의 다리카드가 하나 감소합니다.\n",cur.getPlayerNumber());
                break;
            } else{
                System.out.println("형식이 잘못되었습니다.");
                continue;
            }
        }
        p.UpdateBoard();
        if (cur.getRank() != 0){
            System.out.printf("\nPlayer %d이 %d등으로 도착했습니다.\n",i + 1, cur.getRank());
        }
    }
    public void Winner(){
        int max = 0;
        int max_index = 0;
        for (int i = 0; i < this.player_number; i++){
            Player cur = (Player) this.players.get(i);
            if (cur.getScore() > max){
                max = cur.getScore();
                max_index = i;
            }
        }
        Player cur = (Player) this.players.get(max_index);
        System.out.printf("Player %d가 최종 점수 %d으로 최종 승리하였습니다.\n", cur.getPlayerNumber(), cur.getScore());
    }

    public int getDiceNumber(){
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
}
