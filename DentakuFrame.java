package dentaku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class DentakuFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	JPanel contentPane = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout(0, 0);
	JTextField result = new JTextField("");
	Insets insets = getInsets();
	
	double stackedValue = 0.0; //演算子ボタンを押す前にテキストフィールドにあった値
	boolean isStacked = false; //stackedValueに数値を入力したかどうか
	boolean afterCalc = false; //演算子ボタンを押した後かどうか
	String currentOp = ""; //押された演算子ボタンの名前
	boolean beforeElseAct = false; //演算子ボタンを押したあとに他のボタンを押したかどうか。演算子を押した後、数字ボタンを押してなければ、他の演算子を押して上書きできる。
	
	//ボタン作成
	ClearButton button1 = new ClearButton("AC");
	CalcButton button2 = new CalcButton("%");
	CalcButton button3 = new CalcButton("÷");
	ClearButton button4 = new ClearButton("C");
	CalcButton button5 = new CalcButton("x^y");
	CalcButton button6 = new CalcButton("√");
	CalcButton button7 = new CalcButton("×");
	NumberButton button8 = new NumberButton("7"); 
	NumberButton button9 = new NumberButton("8");
	NumberButton button10 = new NumberButton("9");
	CalcButton button11 = new CalcButton("-");
	NumberButton button12 = new NumberButton("4");
	NumberButton button13 = new NumberButton("5");
	NumberButton button14 = new NumberButton("6");
	CalcButton button15 = new CalcButton("+");
	NumberButton button16 = new NumberButton("1");
	NumberButton button17 = new NumberButton("2");
	NumberButton button18 = new NumberButton("3");
	NumberButton button19 = new NumberButton("0");
	NumberButton button20 = new NumberButton(".");
	CalcButton button21 = new CalcButton("=");
		
	JButton[]buttons = {button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14, button15, button16, button17, button18, button19, button20, button21};//ボタン配列
//	String[]buttonsName ={"AC", "%", "+", "C", "x^y", "√", "×", "7", "8", "9", "-", "4", "5", "6", "+", "1", "2", "3", "=", "0", "."};//ボタンの名前
//	CalcButton[]CButtons = {button2, button3, button5, button6, button7, button11, button15, button21};
	
	public DentakuFrame() {
		contentPane.setLayout(borderLayout1);
		this.setBounds(50, 50, 250, 422);
		this.setTitle("電子式卓上計算機");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setContentPane(contentPane);
//		this.setVisible(true);
		result.setPreferredSize(new Dimension(this.getWidth(),(this.getHeight() - 22) / 7));
		
		contentPane.add(result, BorderLayout.NORTH);//テキストフィールドの配置
		
//		ボタンのサイズ指定
		for (int i = 0; i < buttons.length; i++) {
			if(i == 0 || i == 18 || i == 20) {
				continue;
			}
			buttons[i].setPreferredSize(new Dimension(this.getWidth() / 4, (this.getHeight() - 22)/ 7));//正方形のボタン
		}
		buttons[0].setPreferredSize(new Dimension(124, (this.getHeight() - 22)/ 7));//横長のACボタン
		buttons[18].setPreferredSize(new Dimension(124, (this.getHeight() - 22)/ 7));
		buttons[20].setPreferredSize(new Dimension(this.getWidth() / 4, (this.getHeight() - 22)/ 7 * 2));//縦長の=ボタン
//		marg = button21.getMargin();
		
//		ボタン配置パネル設定
		JPanel keyPanelCenter = new JPanel();//「AC」〜「＋」ボタンを配置するパネル
		keyPanelCenter.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		JPanel keyPanelSouth = new JPanel();//「1」〜「=」一番下のボタンを配置するパネル
		keyPanelSouth.setLayout(new BorderLayout(0, 0));
		JPanel keyPanel16to20 = new JPanel();//「1」〜「.」左下部分ボタンを配置するパネル
		keyPanel16to20.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		JPanel keyPanel21 = new JPanel();//「=」右下のボタンを配置するパネル
		keyPanel21.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		keyPanelSouth.add(keyPanel16to20, BorderLayout.CENTER);
		keyPanelSouth.add(keyPanel21, BorderLayout.EAST);
		
//		「AC」〜「＋」ボタンの配置
		for(int i = 0; i < buttons.length; i++) {
			//ボタンの枠線を表示
			LineBorder border = new LineBorder(Color.black, 1, true);
			buttons[i].setBorder(border);
			if(i >= 0 && i <= 14) {
				keyPanelCenter.add(buttons[i]);
			}
			if(i >= 15 && i <= 19) {
				keyPanel16to20.add(buttons[i]);
			}
			if(i == 20) {
				keyPanel21.add(buttons[i]);
			}
			
		}
		
		contentPane.add(keyPanelCenter, BorderLayout.CENTER);
		contentPane.add(keyPanelSouth, BorderLayout.SOUTH);

		this.setVisible(true);
	}
	
	/* テキストフィールドに引数の文字列をつなげる */
	public void appendResult(String c) {
		if(!afterCalc) { //演算子ボタンを押した直後でないなら
			result.setText(result.getText() + c);
		}else {
			result.setText(c);//押したボタンの文字列だけを設定する（いったんクリアしたかに見える）
			afterCalc = false;
		}
	}
	
	/* 数字を入力するボタンの定義 */
	public class NumberButton extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;
		
		public NumberButton(String keyTop) {
			super(keyTop); //JButtonクラスのコンストラクタを呼び出す
			this.addActionListener(this);//このボタンにアクションイベントのリスナを設定
		}
		
		public void actionPerformed(ActionEvent evt) {
			String keyNumber = this.getText();//ボタンの名前を取り出す
			appendResult(keyNumber);//ボタンの名前をテキストフィールドにつなげる
			beforeElseAct = false;//演算子を押した後に他のボタンを押したかどうか。
		}
	}
	
	//演算子ボタンの定義
	public class CalcButton extends JButton implements ActionListener{
		private static final long serialVersionUID = 1L;
		
		public CalcButton(String op) {
			super(op);
			this.addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent e) {
			if(this.getText().equals("√")) {//ルートだけは他のボタンと違い、一度押したら計算が実行される
				result.setText(String.valueOf(Math.sqrt(Double.valueOf(result.getText()))));
				stackedValue = (Double.valueOf(result.getText())).doubleValue();
				return;
			}
			if(isStacked) {//以前に演算子ボタンが押されたのなら計算結果を出す
				if(beforeElseAct) {
					currentOp = this.getText();//ボタン名から押されたボタンの演算子を取り出す
					return;
				}
				double resultValue = (Double.valueOf(result.getText())).doubleValue();
				if(currentOp.equals("+")) {//演算子によって計算
					stackedValue += resultValue;
				}
				else if(currentOp.equals("-")) {
					stackedValue -= resultValue;
				}
				else if(currentOp.equals("×")) {
					stackedValue *= resultValue;
				}
				else if(currentOp.equals("÷")) {
					stackedValue /= resultValue;
				}
				else if(currentOp.equals("%")) {
					stackedValue %= resultValue;
				}
				else if(currentOp.equals("x^y")) {
					stackedValue = Math.pow(stackedValue, resultValue);
				}
				result.setText(String.valueOf(stackedValue));
			}
			currentOp = this.getText();//ボタン名から押されたボタンの演算子を取り出す
			stackedValue = (Double.valueOf(result.getText())).doubleValue();
			afterCalc = true;
			if (currentOp.equals("＝")) {
				isStacked = false;
			}else {
				isStacked = true;
			}
			beforeElseAct = true;//演算子を押した後に他のボタンを押したかどうか。
			
		}
	}
	
	//クリアボタンの定義
	public class ClearButton extends JButton implements ActionListener{
		private static final long serialVersionUID = 1L;
		
		public ClearButton(String c) {
			super(c);
			this.addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent evt) {
			//AllClearの場合、全てリセット
			if(this.getText().equals("AC")) {
				stackedValue = 0.0;
				afterCalc = false;
				isStacked = false;
				result.setText("");
			}
			//Clearの場合stackedValueがあれば0を表示、stackedValueがなければAllClear
			if(this.getText().equals("C")) {
				if(isStacked) {
					result.setText("0");
				}else {
					stackedValue = 0.0;
					afterCalc = false;
					isStacked = false;
					result.setText("");
				}
			}
				
		}
	}
	
}
