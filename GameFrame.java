import java.util.* ;
import java.awt.* ;
import javax.swing.* ;
import java.awt.event.* ;


public class GameFrame extends JFrame {
	
		
	//count
	int count_time ;
	JLabel count ;
	
	//start game in random 
	JButton random ;
	
	//call AI to finish game
	JButton ai ;
	
	//matrix data
	Button [][] matrix  ;
	
	
	public GameFrame() {
		
		this.setTitle("GF2 Game") ;
		this.setLocation(200,200);
		this.setSize(400,400);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//set label
		count = new JLabel("Hello World") ;
		Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 20) ;		
		count.setFont(font);
		count.setBounds(80,10,240,40);		
		this.add(count) ;
		
		//set random
		random = new JButton("start") ;
		random.setBounds(80,50,80,40);				
		this.add(random) ;
		
		//set random
		ai = new JButton("AI") ;
		ai.setBounds(240,50,80,40);				
		this.add(ai) ;
		
		//set matrix
		matrix = new Button[3][3] ;		
		for(int i=0 ; i<matrix.length ; i++) {
			for(int j=0 ; j<matrix[i].length ; j++) {
				matrix[i][j] = new Button() ;				
				matrix[i][j].setBounds(j*80+80,i*80+100,80,80);
				font = new Font(Font.DIALOG_INPUT, Font.BOLD, 90) ;
				matrix[i][j].setFont(font);
				matrix[i][j].setLabel("■");
				this.add(matrix[i][j]) ;
			}
		}
		
		
		
		
		//set random event
		random.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				count_time = 0 ;
				
				for(int i=0 ; i<matrix.length ; i++) {
					for(int j=0 ; j<matrix[i].length ; j++) {						
						int ran = ((int)(Math.random()*2)) %2 ;												
						if(ran ==0) {
							Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 90) ;
							matrix[i][j].setFont(font);
							matrix[i][j].setLabel("■");
						}
						else {
							matrix[i][j].setLabel("");
						}
					}
				}
				
				count.setText("Hello World");
				
				if(count_light(matrix)==matrix.length*matrix.length) {
					count.setText("Lucky 7!!!!");
				}
				
			}
		});


		//set ai event
		ai.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				count_time = 0 ;
				
				LinkedList<String> list = new LinkedList<String>() ;
				HashMap<String,String> map = new HashMap<String,String>() ; 

				for(int i=0 ; i<matrix.length ; i++) {
					for(int j=0 ; j<matrix[i].length ; j++) {
						//System.out.println(i+","+j+"="+matrix[i][j].getLabel()) ;
						//if not empty
						if("■".equals(matrix[i][j].getLabel())) {						
							add_ans(list,i,j) ;
							add_ans(map,i,j) ;
						}
					}
				}
				
				
				//map version				
				Iterator iter = map.keySet().iterator() ;
				
				while(iter.hasNext()) {
					
					int press = Integer.parseInt((String) iter.next()) ;
					
					int row = press/matrix.length ;
					int column = press%matrix.length ;
					
					System.out.println("press " + row + "," + column) ;
					
					inverseValue(matrix,row,column) ;
					inverseSurround(matrix,row,column);
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					
				}
				
				if(count_light(matrix)==matrix.length*matrix.length) {
					count.setText("Winner!!!!");
				}
				
								
				/*
				//list version
				for(int i=0 ; i<list.size() ; i++) {
					int press = Integer.parseInt(list.get(i)) ;
									
					int row = press/matrix.length ;
					int column = press%matrix.length ;
					
					System.out.println(i+": press " + row + "," + column) ;
					
					inverseValue(matrix,row,column) ;
					inverseSurround(matrix,row,column);
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if(count_light(matrix)==matrix.length*matrix.length) {
						count.setText("Winner!!!!");
					}
								
				}
				*/
			}
		});
		
		
		//set matrix event
		for(int i=0 ; i<matrix.length ; i++) {
			for(int j=0 ; j<matrix[i].length ; j++) {
				matrix[i][j].addActionListener(new ActionListener() {					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						count_time++ ;
						
						for(int i=0 ; i<matrix.length ; i++) {
							for(int j=0 ; j<matrix[i].length ; j++) {								
								if(matrix[i][j] == e.getSource()) {
									inverseValue(matrix, i, j);
									inverseSurround(matrix, i, j) ;
									break ;
								}					
							}
						}
						
						if(count_light(matrix)==matrix.length*matrix.length) {
							count.setText("Winner!!!!");
						}
						else {
							count.setText("You press " + count_time + " times.");
						}
					}
				});
			}
		}
		
		this.setVisible(true) ;
		
		
	}
	
	public void inverseValue(Button [][] matrix , int i , int j) {
		if("".equals(matrix[i][j].getLabel())) {
			Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 90) ;
			matrix[i][j].setFont(font);
			matrix[i][j].setLabel("■");
		}
		else {
			matrix[i][j].setLabel("");
		}
	}
	
	public int count_light(Button [][] matrix) {
		
		int count = 0 ;
		
		for(int i=0 ; i<matrix.length ; i++) {
			for(int j=0 ; j<matrix[i].length ; j++) {
				if("".equals(matrix[i][j].getLabel())) {
					count++ ;
				}
			}
		}
		
		return count ;
	}
	
	public void inverseSurround(Button [][] matrix , int i , int j) {
				
		if( (i-1)>=0 ) {
			inverseValue(matrix,i-1,j);			
		}
		
		if((j-1)>=0) {
			inverseValue(matrix,i,j-1);
		}
		
		if( (i+1)<matrix.length ) {
			inverseValue(matrix,i+1,j);			
		}
		
		if( (j+1)<matrix[i].length ) {			
			inverseValue(matrix,i,j+1);
		}
		
	}
	
	public void add_ans(LinkedList<String> list , int row , int column) {
		
		int index = 3*row + column ;
		
		int [][] ans = {				
				{1,0,1,0,0,1,1,1,0} ,
				{0,0,0,0,1,0,1,1,1} ,
				{1,0,1,1,0,0,0,1,1} ,
				{0,0,1,0,1,1,0,0,1} ,
				{0,1,0,1,1,1,0,1,0} ,
				{1,0,0,1,1,0,1,0,0} ,
				{1,1,0,0,0,1,1,0,1} ,
				{1,1,1,0,1,0,0,0,0} ,
				{0,1,1,1,0,0,1,0,1}
		} ;
		
		for(int i=0 ; i<ans.length ; i++) {
			if(ans[i][index]==1) {
				list.add(""+i) ;
			}
		}		
		
	}
	
	public void add_ans(HashMap<String,String> map , int row , int column) {
		
		int index = 3*row + column ;
		
		int [][] ans = {				
				{1,0,1,0,0,1,1,1,0} ,
				{0,0,0,0,1,0,1,1,1} ,
				{1,0,1,1,0,0,0,1,1} ,
				{0,0,1,0,1,1,0,0,1} ,
				{0,1,0,1,1,1,0,1,0} ,
				{1,0,0,1,1,0,1,0,0} ,
				{1,1,0,0,0,1,1,0,1} ,
				{1,1,1,0,1,0,0,0,0} ,
				{0,1,1,1,0,0,1,0,1}
		} ;
		
		for(int i=0 ; i<ans.length ; i++) {
			if(ans[i][index]==1) {
				if(map.get(i+"")==null) {
					System.out.println("add " + i) ;
					map.put(i+"","add") ;
				}
				else {
					System.out.println("remove " + i) ;
					map.remove(i+"") ;					
				}
			}
		}		
		
	}
	

}
