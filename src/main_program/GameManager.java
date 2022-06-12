package main_program;

import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

public class GameManager {
    private Vector players = new Vector();
    private Vector map_data;
    private String [][]board;
    private int board_row;
    private int board_col;
    private int start_idx;
    private int end_row;
    private int end_col;

    public GameManager(int player_number) throws IOException{
        int i;
        for (i = 0; i < player_number; i++) {
            Player p = new Player(i + 1);
            players.add(p);
        }
        Map map = new Map();
        map_data = map.getMapData();
        this.start_idx = this.InitRowCol();
        for (i = 0; i < player_number; i++) { //플레이어 포지션 초기화.
            Player p = (Player) players.get(i);
            p.setPosition(this.start_idx, 0);
        }
        this.board = this.MakeBoard(start_idx);
    }
    public void PlayerMove(Player cur, String route){
        int cur_row, cur_col;
        Player p;
        for (int i = 0; i < route.length(); i++){ // 해당 루트로 이동 가능한지
            cur_row = cur.getRow();
            cur_col = cur.getCol();
            if (route.charAt(i) == 'u' || route.charAt(i) == 'U'){
                cur.setPosition(cur_row-1, cur_col);
            } else if (route.charAt(i) == 'd' || route.charAt(i) == 'D'){
                cur.setPosition(cur_row+1, cur_col);
            } else if (route.charAt(i) == 'l' || route.charAt(i) == 'L'){
                cur.setPosition(cur_row, cur_col-1);
            } else if (route.charAt(i) == 'r' || route.charAt(i) == 'R'){
                if (this.board[cur_row][cur_col].charAt(0) == 'B'){
                    cur.setPosition(cur_row, cur_col+2);
                    cur.setBridgeCard(1);
                }
                else{
                    cur.setPosition(cur_row, cur_col+1);
                }
            }
            cur_row = cur.getRow();
            cur_col = cur.getCol();
            if (this.board[cur_row][cur_col].charAt(0) == 'E'){
                int count = 0;
                for (int j = 0; j < this.players.size(); j++){
                    p =(Player) this.players.get(j);
                    if (p.getRank() != 0){
                        count++;
                    }
                }
                if (count == 3){
                    return;
                }
                cur.setRank(count+1);
                return;
            }
            else if (this.board[cur_row][cur_col].charAt(0) == 'P'){
                cur.setToolCard(1, 0, 0);
            } else if (this.board[cur_row][cur_col].charAt(0) == 'H'){
                cur.setToolCard(0, 1, 0);
            } else if (this.board[cur_row][cur_col].charAt(0) == 'S'){
                cur.setToolCard(0, 0, 1);
            }
        }
    }
    public boolean CheckMove(Player cur, String route, int go){
        if (!CheckRoute(route)){ //길 자체가 적절한가
            return false;
        }
        if (route.length() != go){ // 길이 이동가능한 칸 수 만큼 인가
            return false;
        }
        if (this.CheckEndPlayer()){ // 도착점에 도착한 플레이어가 있는가
            for (int i = 0; i < route.length(); i++){
                if (route.charAt(i) == 'l' || route.charAt(i) == 'L'){
                    return false;
                }
            }
            String position = new String();
            int map_row = start_idx;
            int map_col = 0;
            int pos = 0;
            int j;
            for (j = 0; j < map_data.size(); j++){
                position = (String) this.map_data.get(j);
                if (map_col == cur.getCol() && map_row == cur.getRow()){
                    pos = j;
                    break;
                }
                if (position.charAt(position.length() - 1) == 'R'){
                    map_col++;
                }
                else if (position.charAt(position.length()-1) == 'D'){
                    map_row++;
                }
                else if (position.charAt(position.length()-1) == 'U'){
                    map_row--;
                }
            }
            for (int i = 0; i < route.length(); i++){
                position = (String) this.map_data.get(pos);
                if (position.charAt(position.length() - 1) == 'E'){
                    return true;
                }
                if (position.charAt(position.length() - 1) == 'D'){
                    if (route.charAt(i) == 'u' || route.charAt(i) == 'U'){
                        return false;
                    } else if (route.charAt(i) == 'd' || route.charAt(i) == 'D'){
                        position = (String) this.map_data.get(++pos);
                    } else{
                        if (position.charAt(0) != 'B'){
                            return false;
                        }
                        else{
                            int count = 0;
                            String str = (String) this.map_data.get(pos);
                            while (str.charAt(str.length() - 1) != 'R'){
                                str = (String) this.map_data.get(pos + count + 1);
                                count++;
                            }
                            int count2 = 0;
                            while (str.charAt(str.length() - 1) == 'R'){
                                str = (String) this.map_data.get(pos + count + count2 + 2);
                                count2++;
                            }
                            pos = pos + 2*count + count2+1;
                        }
                    }
                }
                else if (position.charAt(position.length() - 1) == 'U'){
                    if (route.charAt(i) == 'd' || route.charAt(i) == 'D'){
                        return false;
                    }
                    else if (route.charAt(i) == 'u' || route.charAt(i) == 'U') {
                        position = (String) this.map_data.get(++pos);
                    }
                    else {
                        if (position.charAt(0) != 'B'){
                            return false;
                        }
                        else{
                            int count = 0;
                            String str = (String) this.map_data.get(pos);
                            while (str.charAt(str.length() - 1) != 'R'){
                                str = (String) this.map_data.get(pos + count + 1);
                                count++;
                            }
                            int count2 = 0;
                            while (str.charAt(str.length() - 1) == 'R'){
                                str = (String) this.map_data.get(pos + count + count2 + 2);
                                count2++;
                            }
                            pos = pos + 2*count + count2+1;
                        }
                    }
                }
                else{
                    if (route.charAt(i) != 'r' && route.charAt(i) != 'R'){
                        return false;
                    }
                    position = (String) this.map_data.get(++pos);
                }
            }
        }
        int cur_row = cur.getRow();
        int cur_col = cur.getCol();
        for (int i = 0; i < route.length(); i++){ // 해당 루트로 이동 가능한지
            if (this.board[cur_row][cur_col].charAt(0) == 'E'){
                return true;
            } else if (route.charAt(i) == 'u' || route.charAt(i) == 'U'){
                if (cur_row-1 < 0){
                    return false;
                }
                if (this.board[cur_row-1][cur_col].equals("o")){
                    return false;
                }
                cur_row -= 1;
            } else if (route.charAt(i) == 'd' || route.charAt(i) == 'D'){
                if (cur_row+1 >= this.board_row){
                    return false;
                }
                if (this.board[cur_row+1][cur_col].equals("o")){
                    return false;
                }
                cur_row += 1;
            } else if (route.charAt(i) == 'l' || route.charAt(i) == 'L'){
                if (cur_col-1 < 0){
                    return false;
                }
                if (this.board[cur_row][cur_col-1].equals("o")){
                    return false;
                }
                cur_col -= 1;
            } else if (route.charAt(i) == 'r' || route.charAt(i) == 'R'){
                if (cur_col+1 >= this.board_col){
                    return false;
                }
                if (this.board[cur_row][cur_col+1].equals("o") && this.board[cur_row][cur_col].charAt(0) == 'B'){
                    cur_col += 2;
                }
                else if (this.board[cur_row][cur_col+1].equals("o")){
                    return false;
                }
                else{
                    cur_col += 1;
                }
            }
        }
        return true;
    }
    public boolean CheckEndPlayer(){
        this.players = this.getPlayers();
        for (int i = 0; i < this.players.size(); i++){
            Player cur = (Player) this.players.get(i);
            if (cur.getRank() != 0){
                return true;
            }
        }
        return false;
    }
    public boolean CheckEnd(){
        int map_size = this.map_data.size();
        this.players = this.getPlayers(); // 후에 수정
        int player_num = this.players.size();
        int count = 0;
        for (int k = 0; k < player_num; k++) {
            Player cur = (Player) this.players.get(k);
            if (cur.getRank() != 0){
                count++;
            }
        }
        if (count == player_num - 1){
            if (count == 2){
                for (int k = 0; k < player_num; k++) {
                    Player cur = (Player) this.players.get(k);
                    if (cur.getRank() == 0){
                        cur.setRank(count+1);
                    }
                }
            }
            return true;
        }
        return false;
    }
    public void UpdateBoard(){
        this.board = this.MakeBoard(this.start_idx);
    }
    public String[][] MakeBoard(int start_idx){
        this.players = this.getPlayers(); // 후에 수정
        String [][]gameboard = new String[this.board_row][this.board_col];
        String str;
        int map_size = this.map_data.size();
        int player_num = this.players.size();
        for (int i = 0; i < this.board_row; i++){ // 게임보드 'o'로 초기화.
            for (int j = 0; j < this.board_col; j++){
                gameboard[i][j] = "o";
            }
        }
        int map_idx;
        int i = start_idx;
        int j = 0;
        for (map_idx = 0; map_idx < map_size; map_idx++){ // 맵에 길 채우기.
            str = (String) this.map_data.get(map_idx);
            if (i == start_idx && j == 0)
            {
                gameboard[i][j] = "F"; // 첫 시작
            }
            else{
                gameboard[i][j] = String.valueOf(str.charAt(0));
            }
            if (str.charAt(str.length()-1) == 'R'){
                j++;
            }
            else if (str.charAt(str.length()-1) == 'L'){
                j--;
            }
            else if (str.charAt(str.length()-1) == 'D'){
                i++;
            }
            else if (str.charAt(str.length()-1) == 'U'){
                i--;
            }
        }
        for (i = 0; i < this.board_row; i++){ // 끝점 파악
            for (j = 0; j < this.board_col; j++){
                if (gameboard[i][j].equals("E")){
                    this.end_col = i;
                    this.end_row = j;
                }
            }
        }
        for (int k = 0; k < player_num; k++){ // 맵에 플레이어 추가.
            Player cur = (Player) this.players.get(k);
            gameboard[cur.getRow()][cur.getCol()] = gameboard[cur.getRow()][cur.getCol()] + String.valueOf(cur.getPlayerNumber());
        }
        this.board = gameboard;
        return gameboard;
    }
    public Vector getPlayers(){ return this.players;}
    public String[][] getBoard(){ return this.board;}
    public int getRow(){ return this.board_row;}
    public int getCol(){ return this.board_col;}
    private int InitRowCol(){
        int start_idx = 0;
        int row = 0;
        int col = 1;
        int row_max = 0;
        int start_min = 0;
        String str;
        int map_size = this.map_data.size();
        for (int i = 0; i < map_size; i++){
            str = (String) this.map_data.get(i);
            if (str.charAt(str.length()-1) == 'R'){
                col++;
                if (row != 0){
                    if (row_max < row){
                        row_max = row;
                    }
                    row = 0;
                    if (start_idx < start_min){
                        start_min = start_idx;
                    }
                }
            }
            else if (str.charAt(str.length()-1) == 'L'){
                col--;
            }
            else if (str.charAt(str.length()-1) == 'D'){
                row++;
                start_idx++;
            }
            else if (str.charAt(str.length()-1) == 'U'){
                row++;
                start_idx--;
            }
            else if (str.charAt((str.length()-1))== 'E'){
                if (row != 0){
                    if (row_max < row){
                        row_max = row;
                    }
                    if (start_idx < start_min){
                        start_min = start_idx;
                    }
                }
            }
        }
        row = row_max+1;
        this.board_col = col;
        this.board_row = row;
        if (start_min < 0){
            return start_min * -1;
        }
        return start_min;
    }
    private boolean CheckRoute(String route){
        for (int i = 0; i < route.length(); i++){ // 길 자체가 합당한가.
            if (!isInRoute(route.charAt(i))){
                return false;
            }
        }
        return true;
    }
    private boolean isInRoute(char a){
        Vector v = new Vector<>(Arrays.asList('u', 'l', 'r', 'd', 'U', 'L', 'R', 'D'));
        for (int i = 0; i < v.size(); i++) {
            if (a == (char)v.get(i)){
                return true;
            }
        }
        return false;
    }
}
