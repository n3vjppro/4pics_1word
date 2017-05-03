package com.example.mypc.a4pics_1word;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private LinearLayout layout_2;
    private LinearLayout layout_3;
    private LinearLayout layout_4;
    private LinearLayout layout_5;
    private Button[] btnKq, btnAsw;
    private int idAsw = 0;
    private int idQs = 0;
    private int flag = 0;
    private String tmp = "";
    private MyDatabase mDatabase;
    private int mCurrentQuestion = 1;

    public static final int[] QUESTIONS_3 = {
            0,//Bỏ qua phần tử 0
            R.drawable.img_3_war,
            R.drawable.img_3_wax,
            R.drawable.img_3_way,
            R.drawable.img_3_wet,
            R.drawable.img_3_wig,
            R.drawable.img_3_wok,
            R.drawable.img_3_yak,
            R.drawable.img_3_zen,
            R.drawable.img_3_zip,
    };
    public static final String[] ANSWER_3 = {
            "",//Bỏ qua phần tử 0
            "WAR",
            "WAX",
            "WAY",
            "WET",
            "WIG",
            "WOK",
            "YAK",
            "ZEN",
            "ZIP",
    };
    public static final int[] QUESTIONS_4 = {
            0,//Bỏ qua phần tử 0
            R.drawable.img_4_snap,
            R.drawable.img_4_tube,
            R.drawable.img_4_tuna,
            R.drawable.img_4_tune,
            R.drawable.img_4_twig,
            R.drawable.img_4_type,
            R.drawable.img_4_unit,
    };
    public static final String[] ANSWER_4 = {
            "",//Bỏ qua phần tử 0
            "SNAP",
            "TUBE",
            "TUNA",
            "TUNE",
            "TWIG",
            "TYPE",
            "UNIT",
    };
    public static final int[] QUESTIONS_5 = {
            0,//Bỏ qua phần tử 0
            R.drawable.img_5_write,
            R.drawable.img_5_wrong,
            R.drawable.img_5_yacht,
            R.drawable.img_5_young,
            R.drawable.img_5_zebra,
    };
    public static final String[] ANSWER_5 = {
            "",//Bỏ qua phần tử 0
            "WRITE",
            "WRONG",
            "YACHT",
            "YOUNG",
            "ZEBRA",
    };
    public static final int[] QUESTIONS_6 = {
            0,//Bỏ qua phần tử 0
            R.drawable.img_6_athens,
            R.drawable.img_6_attach,
            R.drawable.img_6_caress,
    };
    public static final String[] ANSWER_6 = {
            "",//Bỏ qua phần tử 0
            "ATHENS",
            "ATTACH",
            "CARESS",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Khởi tạo CSDL
        mDatabase = new MyDatabase(this);
        initListLetter();
        createButtonQuestion(3, mCurrentQuestion);
        //Tạo image, tham số là ID={3,4,5,6}
        createImage(3, mCurrentQuestion);
    }

    //Khởi tạo CSDL
    public void initListLetter() {
        int startID = 3;//Bắt đầu là 3 letters
        mDatabase.open();
        int countRecords = mDatabase.countRecords();
        mDatabase.close();
        if (countRecords <= 0) {
            String[] letters = {
                    " Three letters",
                    " Four letters",
                    " Five letters",
                    " Six letters",
            };

            int[] maxQuestions = {
                    QUESTIONS_3.length - 1,
                    QUESTIONS_4.length - 1,
                    QUESTIONS_5.length - 1,
                    QUESTIONS_6.length - 1,
            };

            for (int i = startID; i < letters.length + startID; i++) {
                Letter letter = new Letter();
                letter.setId(i);
                letter.setLetter(letters[i - startID]);
                letter.setCoin(0);
                letter.setCurrentQuestion(1);
                letter.setMaxQuestion(maxQuestions[i - startID]);

                mDatabase.open();
                mDatabase.addItem(letter);
                mDatabase.close();
            }
        }

    }

    //Lấy câu hỏi hiện tại từ ID
    public int getCurrentQuestion(int id) {
        int currentQuestion;
        List<Letter> letters = new ArrayList<>();

        //Select currentQuestion from TableDB where ID=id
        mDatabase.open();
        letters = mDatabase.getListByID(id);
        mDatabase.close();

        currentQuestion = letters.get(0).getCurrentQuestion();

        return currentQuestion;
    }

    //Hiển thị hình ảnh
    public void createImage(int id, int curentQuestion) {

        layout_2 = (LinearLayout) findViewById(R.id.layout_2);
        ImageView iv = new ImageView(this);

        switch (id) {
            case 3:
                iv.setImageResource(QUESTIONS_3[curentQuestion]);
                break;
            case 4:
                iv.setImageResource(QUESTIONS_4[curentQuestion]);
                break;
            case 5:
                iv.setImageResource(QUESTIONS_5[curentQuestion]);
                break;
            case 6:
                iv.setImageResource(QUESTIONS_6[curentQuestion]);
        }

        layout_2.addView(iv);

        createButton(id);
    }

    //Tạo button điền kết quả
    public void createButton(int id) {
        layout_3 = (LinearLayout) findViewById(R.id.layout_3);
        int lengthButton = id;
        btnKq = new Button[lengthButton];

        for (idQs = 0; idQs < lengthButton; idQs++) {
            final Button btn = new Button(this);
            btn.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            btn.setId(idQs+111);
            layout_3.addView(btn);
            btnKq[idQs] = (Button) findViewById(btn.getId());
            btnKq[idQs].setEnabled(false);
            btnKq[idQs].setText("");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < 12; i++) {
                        if (btnAsw[i].getText() == "") {
                            btnAsw[i].setText(btnKq[(Integer.parseInt(btn.getId()+"")-111)].getText());
                            Log.e("aaa", btn.getId()+"");
                            btnAsw[i].setVisibility(View.VISIBLE);
                            flag--;
                            btnKq[btn.getId()-111].setText("");
                            btnKq[btn.getId()-111].setEnabled(false);
                            break;
                        }
                    }
                    if (flag < 0) flag = 0;
                    Log.e("aaaa", flag + "//");

                }
            });
        }
    }

    public void createButtonQuestion(final int id, final int question) {
        layout_4 = (LinearLayout) findViewById(R.id.layout_4);
        layout_5 = (LinearLayout) findViewById(R.id.layout_5);
        final int lengthButton = 12;
        btnAsw = new Button[lengthButton];
        mDatabase.open();


        switch (id) {
            case 3:
                tmp = ANSWER_3[question];
                break;
            case 4:
                tmp = ANSWER_4[question];
                break;
            case 5:
                tmp = ANSWER_5[question];
                break;
            case 6:
                tmp = ANSWER_6[question];
                break;
        }
        Log.e("aaaa", tmp + "////" + id);
        Random rd = new Random();
        String arr[] = new String[12];
        for (int i = 0; i < tmp.length(); i++) {
            boolean check = true;
            while (check) {
                int rdd = rd.nextInt(12);
                if (arr[rdd] == null) {
                    arr[rdd] = tmp.charAt(i) + "";
                    break;
                } else continue;
            }
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                arr[i] = ((char) (rd.nextInt(25) + 65)) + "";
            }
        }
        for (int i =0; i<arr.length;i++){
            Log.e("aaaa", arr[i]+"/////");
        }


        for (idAsw = 0; idAsw < 6; idAsw++) {
            final Button btn = new Button(this);
            btn.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            btn.setId(idAsw);
            layout_4.addView(btn);
            btnAsw[idAsw] = (Button) findViewById(btn.getId());
            btnAsw[idAsw].setText(arr[idAsw] + "");
            Log.e("aaaa", flag + "//");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag == 6) ;
                    else {
                        btnKq[flag].setText(btnAsw[btn.getId()].getText());
                        btnKq[flag].setEnabled(true);
                        flag++;
                        btnAsw[btn.getId()].setText("");
                        btnAsw[btn.getId()].setVisibility(View.INVISIBLE);
                        Log.e("aaaa", flag + "//");
                        if (flag == id) {
                            String answer="";
                            for (Button b: btnKq
                                    ) {
                                answer+=b.getText();
                            }
                            if (answer.equals(tmp)){

                                layout_2.removeAllViews();
                                layout_4.removeAllViews();
                                layout_5.removeAllViews();
                                layout_3.removeAllViews();
                                btnKq = new Button[id];
                                btnAsw = new Button[12];
                                idQs = 0;
                                idAsw = 0;
                                Toast.makeText(MainActivity.this, "WRONG", Toast.LENGTH_SHORT).show();
                                mCurrentQuestion++;
                                createImage(id, mCurrentQuestion);
                                createButtonQuestion(id, mCurrentQuestion);
                            }
                            else  {
                                layout_2.removeAllViews();
                                layout_4.removeAllViews();
                                layout_5.removeAllViews();
                                layout_3.removeAllViews();
                                btnKq = new Button[id];
                                btnAsw = new Button[12];
                                idQs = 0;
                                idAsw = 0;
                                createImage(id, mCurrentQuestion);
                                createButtonQuestion(id, mCurrentQuestion);
                            }
                            flag = 0;
                        }
                    }
                }
            });
        }
        for (idAsw = 6; idAsw < 12; idAsw++) {
            final Button btn = new Button(this);
            btn.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            btn.setId(idAsw);
            layout_5.addView(btn);
            btnAsw[idAsw] = (Button) findViewById(btn.getId());
            btnAsw[idAsw].setText(arr[idAsw] + "");
            Log.e("aaaa", flag + "//");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag == 6) ;
                    else {
                        btnKq[flag].setText(btnAsw[btn.getId()].getText());
                        btnKq[flag].setEnabled(true);
                        flag++;
                        btnAsw[btn.getId()].setText("");
                        btnAsw[btn.getId()].setVisibility(View.INVISIBLE);
                        Log.e("aaaa", flag + "//");
                        if (flag == id) {
                            String answer="";
                            for (Button b: btnKq
                                 ) {
                                answer+=b.getText();
                            }
                            if (answer.equals(tmp)){
                                Toast.makeText(MainActivity.this,"CONGRATULATIONS! The question is: "+tmp, Toast.LENGTH_LONG).show();
                                layout_2.removeAllViews();
                                layout_4.removeAllViews();
                                layout_5.removeAllViews();
                                layout_3.removeAllViews();
                                btnKq = new Button[id];
                                btnAsw = new Button[12];
                                idQs = 0;
                                idAsw = 0;
                                mCurrentQuestion++;
                                createImage(id, mCurrentQuestion);
                                createButtonQuestion(id, mCurrentQuestion);
                            }
                            else  {
                                layout_2.removeAllViews();
                                layout_4.removeAllViews();
                                layout_5.removeAllViews();
                                layout_3.removeAllViews();
                                btnKq = new Button[id];
                                btnAsw = new Button[12];
                                idQs = 0;
                                idAsw = 0;
                                createImage(id, mCurrentQuestion);
                                createButtonQuestion(id, question);
                            }
                            //Toast.makeText(MainActivity.this, "///"+tmp+"///", Toast.LENGTH_SHORT).show();
                            flag = 0;
                        }
                    }
                }
            });
        }
    }
}
