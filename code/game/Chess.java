import java.awt.EventQueue;

public class Chess extends JFrame {
	
	/**
	 * this is an object which shows the process of chess board.
	 */
	private int tile=1;                                                  //覆盖级数
	private long delay = 800;                                            //延迟时间
	Color[] colors = { Color.yellow, Color.green, Color.red, Color.blue, Color.orange, Color.white, Color.pink};     //颜色数组
	//static int[][] board;
    
	Label label4[][]= new Label[4][4];                                   // label数组
	Label label8[][]= new Label[8][8];
	Label label16[][]= new Label[16][16];
	
	private JPanel contentPane;
	private Panel panel;
	private Panel panel_show16;										     //相应棋盘对应的panel
	private Panel panel_show8;
	private Panel panel_show4;
	private TextField sizeTextField;
	private TextField lineTextField;
	private TextField rowTextField;
	private Label teller;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chess frame = new Chess();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Chess() {
		setTitle("Chessboard");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		{
			sizeTextField = new TextField();
			sizeTextField.setBounds(116, 376, 88, 23);
			contentPane.add(sizeTextField);
		}
		//start 按钮
		{
			Button start_button = new Button("Start");
			start_button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if(sizeTextField.getText().equals(""))
						teller.setText("大小不能为空！");
					else if(sizeTextField.getText().equals("4") ||sizeTextField.getText().equals("8") || sizeTextField.getText().equals("16")){
						if(lineTextField.getText().equals("")||rowTextField.getText().equals(""))
						teller.setText("请输入特殊点坐标！");
						else if(Integer.parseInt(lineTextField.getText())<0||Integer.parseInt(lineTextField.getText())>=Integer.parseInt(sizeTextField.getText())||Integer.parseInt(rowTextField.getText())<0||Integer.parseInt(rowTextField.getText())>=Integer.parseInt(sizeTextField.getText()))
						{
							teller.setText("特殊点输入错误！");
						}   
						else{
							teller.setText("OK!");
							panel.setVisible(false);
							if(sizeTextField.getText().equals("16")){
								panel_show8.setVisible(false);
								panel_show4.setVisible(false);
								label_init(label16);
								panel_show16.setVisible(true);
								label16[Integer.parseInt(lineTextField.getText())][Integer.parseInt(rowTextField.getText())].setBackground(Color.black);
								chessBoard(0, 0, Integer.parseInt(lineTextField.getText()), Integer.parseInt(rowTextField.getText()), 16, label16);
							}
							if(sizeTextField.getText().equals("8")){
								panel_show16.setVisible(false);
								panel_show4.setVisible(false);
								label_init(label8);
								panel_show8.setVisible(true);
								label8[Integer.parseInt(lineTextField.getText())][Integer.parseInt(rowTextField.getText())].setBackground(Color.black);
								chessBoard(0, 0, Integer.parseInt(lineTextField.getText()), Integer.parseInt(rowTextField.getText()), 8, label8);
							}
							if(sizeTextField.getText().equals("4")){
								panel_show16.setVisible(false);
								panel_show8.setVisible(false);
								label_init(label4);
								panel_show4.setVisible(true);
								label4[Integer.parseInt(lineTextField.getText())][Integer.parseInt(rowTextField.getText())].setBackground(Color.black);
								chessBoard(0, 0, Integer.parseInt(lineTextField.getText()), Integer.parseInt(rowTextField.getText()), 4, label4);
							}
							//panel_show.setVisible(true);
						/////////////////////////////////////////////
						}
					}
					else {
						teller.setText("大小不规格！");
					}
				}
			});
			start_button.setFont(new Font("Dialog", Font.BOLD, 12));
			start_button.setBounds(292, 376, 76, 23);
			contentPane.add(start_button);
		}
		//exit 按钮
		{
			Button exit_button = new Button("Exit");
			exit_button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					System.exit(0);
				}
			});
			exit_button.setFont(new Font("Dialog", Font.BOLD, 12));
			exit_button.setBounds(292, 420, 76, 23);
			contentPane.add(exit_button);
		}
		{
			Label sizeLabel = new Label("SIZE\uFF1A");
			sizeLabel.setFont(new Font("Dialog", Font.BOLD, 12));
			sizeLabel.setBounds(37, 376, 45, 23);
			contentPane.add(sizeLabel);
		}
		{
			Label coordLabel = new Label("S   P :");
			coordLabel.setFont(new Font("Dialog", Font.BOLD, 12));
			coordLabel.setBounds(37, 420, 45, 23);
			contentPane.add(coordLabel);
		}
		{
			lineTextField = new TextField();
			lineTextField.setBounds(116, 420, 33, 23);
			contentPane.add(lineTextField);
		}
		{
			rowTextField = new TextField();
			rowTextField.setBounds(171, 420, 33, 23);
			contentPane.add(rowTextField);
		}
		
		{
			teller = new Label("\u53EF\u8F93\u5165size\uFF1A 4\u30018\u300116 \uFF01\uFF01\uFF01sp : \u7279\u6B8A\u70B9\uFF01\uFF01\uFF01");
			teller.setAlignment(Label.CENTER);
			teller.setBounds(73, 346, 271, 23);
			contentPane.add(teller);
		}	
		//for test.start.
		//{
		//	Label aaaaaaaaaaaaaaaaa = new Label("New label");
		//	aaaaaaaaaaaaaaaaa.setBackground(Color.LIGHT_GRAY);
		//	aaaaaaaaaaaaaaaaa.setBounds(13, 334, 69, 23);
		//	contentPane.add(aaaaaaaaaaaaaaaaa);
		//}//for test.end.
		{
			panel = new Panel();
			panel.setBounds(36, 39, 381, 272);
			contentPane.add(panel);
			panel.setLayout(null);
			{
				Label label01 = new Label("Welcome\uFF01");
				label01.setBounds(80, 5, 220, 54);
				panel.add(label01);
				label01.setForeground(Color.GRAY);
				label01.setFont(new Font("Impact", Font.ITALIC, 40));
			}
			{
				Label label02 = new Label("chessboard...");
				label02.setBounds(36, 64, 252, 54);
				panel.add(label02);
				label02.setForeground(Color.GRAY);
				label02.setFont(new Font("Impact", Font.ITALIC, 40));
			}
			{
				Label label03 = new Label("^^");
				label03.setBounds(293, 64, 52, 54);
				panel.add(label03);
				label03.setForeground(Color.GRAY);
				label03.setFont(new Font("Impact", Font.PLAIN, 40));
			}
			{
				JLabel lblHelloYue = new JLabel("Hello YOU");
				lblHelloYue.setForeground(SystemColor.inactiveCaption);
				lblHelloYue.setFont(new Font("Impact", Font.ITALIC, 38));
				lblHelloYue.setBounds(159, 163, 186, 44);
				panel.add(lblHelloYue);
			}
		}
		//十六格格
		{
			panel_show16 = new Panel();
			panel_show16.setBackground(new Color(224, 255, 255));
			panel_show16.setBounds(37, 39, 380, 272);
			panel_show16.setLayout(new GridLayout(16, 0, 5, 5));
			/**
			/ * 建立矩阵图*/
			for(int i=0; i<16; i++)
			{
				label16[i] = new Label[16];
				for(int j=0;j<16;j++){
					label16[i][j] = new Label("");
					//label16[i][j].setFont(new Font("Gulim", Font.PLAIN, 10));
					label16[i][j].setBackground(Color.LIGHT_GRAY);
					panel_show16.add(label16[i][j]);
				}
			}
			contentPane.add(panel_show16);
			panel_show16.setVisible(false);
		}
		//八格格
		{
			panel_show8 = new Panel();
			panel_show8.setBackground(new Color(224, 255, 255));
			panel_show8.setBounds(37, 39, 380, 272);
			panel_show8.setLayout(new GridLayout(8, 0, 5, 5));
			/**
			/ * 建立矩阵图*/
			for(int i=0; i<8; i++)
			{
				label8[i] = new Label[8];
				for(int j=0;j<8;j++){
					label8[i][j] = new Label("");
					//label8[i][j].setFont(new Font("Gulim", Font.PLAIN, 18));
					label8[i][j].setBackground(Color.LIGHT_GRAY);
					panel_show8.add(label8[i][j]);
				}
			}
			contentPane.add(panel_show8);
			panel_show8.setVisible(false);
		}
		//四格格
		{
			panel_show4 = new Panel();
			panel_show4.setBackground(new Color(224, 255, 255));
			panel_show4.setBounds(37, 39, 380, 272);
			panel_show4.setLayout(new GridLayout(4, 0, 5, 5));
			/**
			/ * 建立矩阵图*/
			for(int i=0; i<4; i++)
			{
				label4[i] = new Label[4];
				for(int j=0;j<4;j++){
					label4[i][j] = new Label("");
					//label4[i][j].setFont(new Font("Gulim", Font.PLAIN, 24));
					label4[i][j].setBackground(Color.LIGHT_GRAY);
					panel_show4.add(label4[i][j]);
				}
			}
			contentPane.add(panel_show4);
			panel_show4.setVisible(false);
		}
		//////////////////////////////////////////////////////////
		setBounds(100, 100, 465, 528);
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JMenu menu = new JMenu("\u6E38\u620F");
				menuBar.add(menu);
				{
					JMenuItem Start = new JMenuItem("\u5F00 \u59CB");
					menu.add(Start);
				}
			}
		}
	}
	
	/*
	 * 分治法实现棋盘覆盖算法
	 */
	public void chessBoard(int tr, int tc, int dr, int dc, int size,Label[][] label)
	{
		if(size == 1){
			return;
		}
		int t=tile++;
		//修改延迟时间
		if(t>10)
			delay = 100;
		int  s = size/2;
		if(dr<tr+s && dc<tc+s){                   //覆盖棋盘左上角
			chessBoard(tr,tc,dr,dc,s,label);
		}
		else {
			//if(board[tr+s-1][tr+s-1]==0)
			//board[tr+s-1][tc+s-1] = t;
			//label[tr+s][tc+s].setText(String.valueOf(t));
			label[tr+s-1][tc+s-1].setBackground(colors[t%7]);
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("*****************");
			//showBoard();
			chessBoard(tr,tc,tr+s-1,tc+s-1,s,label);
		}
		
		if(dr<tr+s && dc>=tc+s){                  //覆盖棋盘右上角
			chessBoard(tr,tc+s,dr,dc,s,label);
		}
		else{
			//if(board[tr+s-1][tc+s]==0)
			//board[tr+s-1][tc+s] = t;
			//label[tr+s][tc+s].setText(String.valueOf(t));
			label[tr+s-1][tc+s].setBackground(colors[t%7]);
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("*****************");
			//showBoard();
			chessBoard(tr,tc+s,tr+s-1,tc+s,s,label);
		}
		
		if(dr>=tr+s && dc<tc+s){                  //覆盖棋盘左下角
			chessBoard(tr+s,tc,dr,dc,s,label);
		}
		else{
			//if(board[tr+s][tc+s-1]==0)
			//board[tr+s][tc+s-1] = t;
			//label[tr+s][tc+s].setText(String.valueOf(t));
			label[tr+s][tc+s-1].setBackground(colors[t%7]);
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    //System.out.println("*****************");
			//showBoard();
			chessBoard(tr+s,tc,tr+s,tc+s-1,s,label);
		}
		
		if(dr>=tr+s && dc>=tc+s){                 //覆盖棋盘右下角
			chessBoard(tr+s,tc+s,dr,dc,s,label);
		}
		else{
			//if(board[tr+s][tc+s]==0)
			//board[tr+s][tc+s] = t;
			//label[tr+s][tc+s].setText(String.valueOf(t));
			label[tr+s][tc+s].setBackground(colors[t%7]);
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("*****************");
			//showBoard();
			chessBoard(tr+s,tc+s,tr+s,tc+s,s,label);
		}
		
	}
	/*
	 * label数组初始化
	 * */
	public void label_init(Label[][] label)
	{
		int size = label[0].length;
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++)
				label[i][j].setBackground(Color.LIGHT_GRAY);
		}
	}
}
