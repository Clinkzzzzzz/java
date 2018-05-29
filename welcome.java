import java.util.*;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class welcome extends Application{
	char whoseTurn='X';
	Cell[][] cell=new Cell[10][10];
	int[][] vis=new int[10][10];
	int cnt=0;
	boolean isWon=false;
	Label lblstatus=new Label("X's turn to play");

	public void start(Stage primaryStage){
		GridPane pane=new GridPane();
		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++)
				pane.add(cell[i][j]=new Cell(i,j),j,i);
		BorderPane borderPane=new BorderPane();
		borderPane.setCenter(pane);
		borderPane.setBottom(lblstatus);
		Scene scene=new Scene(borderPane,450,170);
		primaryStage.setTitle("Tictactoe");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
/*
	public boolean isFull(){
		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++)
				if(cell[i][j].getToken()==' ')return false;
		return true;
	}
*/
/*
	public boolean isWon(char token){
		for(int i=0;i<3;i++)
			if(cell[i][0].getToken()==token&&cell[i][1].getToken()==token&&cell[i][2].getToken()==token)return true;

		for(int j=0;j<3;j++)
			if(cell[0][j].getToken()==token&&cell[1][j].getToken()==token&&cell[2][j].getToken()==token)return true;

		if(cell[0][0].getToken()==token&&cell[1][1].getToken()==token&&cell[2][2].getToken()==token)return true;
		if(cell[0][2].getToken()==token&&cell[1][1].getToken()==token&&cell[2][0].getToken()==token)return true;

		return false;
	}
*/
	public void dfs(char token,int x,int y,int dir){
			if(x>9||x<0||y<0||y>9)return;
			if(token!=cell[x][y].getToken()||vis[x][y]!=0)return;
			cnt++;
			vis[x][y]=1;
			if(cnt==5){
				isWon=true;
				return;
			}
			if(dir==0){
				dfs(token,x-1,y,0);
				dfs(token,x+1,y,0);
			}
			if(dir==1){
				dfs(token,x,y-1,1);
				dfs(token,x,y+1,1);
			}
			if(dir==2){
				dfs(token,x-1,y-1,2);
				dfs(token,x+1,y+1,2);
			}
			if(dir==3){
				dfs(token,x-1,y+1,3);
				dfs(token,x+1,y-1,3);
			}
	}
	public class Cell extends Pane{
		char token=' ';
		int x,y;
		public Cell(int x,int y){
			this.x=x;
			this.y=y;
			setStyle("-fx-border-color: black");
			this.setPrefSize(2000,2000);
			this.setOnMouseClicked(e->handleMouseClick());
		}

		public char getToken(){
			return token;
		}
		public int getX(){
			return x;
		}
		public int getY(){
			return y;
		}

		public void setToken(char c){
			token=c;
			if(token=='X'){
				Line line1=new Line(10,10,this.getWidth()-10,this.getHeight()-10);
				line1.endXProperty().bind(this.widthProperty().subtract(10));
				line1.endYProperty().bind(this.heightProperty().subtract(10));
				Line line2=new Line(10,this.getHeight()-10,this.getWidth()-10,10);
				line2.startYProperty().bind(this.heightProperty().subtract(10));
				line2.endXProperty().bind(this.widthProperty().subtract(10));
				this.getChildren().addAll(line1,line2);
			}
			else if(token=='O'){
				Ellipse ellipse=new Ellipse(this.getWidth()/2,this.getHeight()/2,this.getWidth()/2-10,this.getHeight()/2-10);
				ellipse.centerXProperty().bind(this.widthProperty().divide(2));
				ellipse.centerYProperty().bind(this.heightProperty().divide(2));
				ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
				ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
				ellipse.setStroke(Color.BLACK);
				ellipse.setFill(Color.WHITE);
				this.getChildren().add(ellipse);
			}
		}

		private void handleMouseClick(){
			if(token==' '&&whoseTurn!=' '){
				setToken(whoseTurn);
				isWon=false;
				for(int i=0;i<4;i++){

					for(int j=0;j<10;j++)
						for(int k=0;k<10;k++)
							vis[j][k]=0;

					cnt=0;
					dfs(token,x,y,i);
					if(isWon)break;
				}		
				if(isWon){
					lblstatus.setText(whoseTurn+" won! the game is over");
					whoseTurn=' ';
				}
				else{
					whoseTurn=(whoseTurn=='X')?'O':'X';
					lblstatus.setText(whoseTurn+"'s turn");
				}
			}
		}
	}
}