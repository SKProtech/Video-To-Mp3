package com.protech.inc.convertor;
 
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View;
import android.widget.Button;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.view.FilePickerDialog;
import java.io.File;
import com.developer.filepicker.controller.DialogSelectionListener;
import java.util.Arrays;
import android.view.View.OnClickListener;
import java.io.IOException;
import android.widget.Toast;
import android.net.Uri;
import android.os.AsyncTask;
import android.app.ProgressDialog;

public class MainActivity extends Activity { 

//app Design and created by salaudeen jamiu 
//Time 12.59pm
//Date 17/March/2021
//Copyright All Right Resoved

/*Copyright [2021] [Salaudeen Jamiu]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

private LinearLayout line1;
private LinearLayout line2;
private EditText edittextV;
private EditText edittextDir;
private ImageView Vpick;
private ImageView Dpick;
private Button converte;
private String videoData;
private String dirData;
private String replaceData;
private int check;
private int check2;
private ProgressDialog prog;
private android.graphics.drawable.GradientDrawable pr = new android.graphics.drawable.GradientDrawable();
private android.graphics.drawable.GradientDrawable prb = new android.graphics.drawable.GradientDrawable();
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		setTitle("Video To Mp3");
		check = 0;
		check2 = 0;
		
		
		
		line1 = findViewById(R.id.line1);
		line2 = findViewById(R.id.line2);
		edittextV = findViewById(R.id.editextV);
		edittextDir = findViewById(R.id.editextDir);
		Vpick = findViewById(R.id.Vpick);
		Dpick = findViewById(R.id.Dpick);
		converte = findViewById(R.id.converte);
		
		
		
		
		edittextV.setEnabled(false);
		edittextV.setTextSize(12);
		edittextDir.setTextSize(12);
		edittextDir.setEnabled(false);
		
		
		//Round Corner with shadow Start
		pr.setColor(0xfffffffff);
		pr.setCornerRadius(10);
		line1.setElevation(5);
		line2.setElevation(5);
		line1.setBackgroundColor(0xfffffffff);
		line2.setBackground(pr);
		line1.setBackground(pr);
		Vpick.performClick();
		Dpick.performClick();
	    //button design
	    prb.setColor(0xfff2196f3);
	    prb.setCornerRadius(15);
	    converte.setBackground(prb);
		//Round Corner with shadow End
		
		
		//pick video
		Vpick.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View p1){
				
				DialogProperties protech = new DialogProperties();
				protech.selection_mode = DialogConfigs.SINGLE_MODE;
				protech.selection_type = DialogConfigs.FILE_SELECT;
				protech.root = new File("/storage/emulated/0/");
				protech.error_dir = new File("/storage/emulated/0/");
				protech.offset = new File(DialogConfigs.DEFAULT_DIR);
				protech.extensions = null;
				protech.show_hidden_files = false;
				FilePickerDialog dialog = new FilePickerDialog(MainActivity.this,protech);
				dialog.setTitle("Select a File");
				dialog.setDialogSelectionListener(new DialogSelectionListener() {
						@Override
						public void onSelectedFilePaths(String[] files) {
							check = 1;

							edittextV.setText(Arrays.asList(files).get(0).toString());
							videoData = Arrays.asList(files).get(0).toString();

						}
					});
				dialog.show();
				
				
				
			}
		});
		
		
	   //pick folder to save audio to
	   Dpick.setOnClickListener(new View.OnClickListener(){
		 
		 @Override
		 public void onClick(View p2)
		 {
			 
			 
			 DialogProperties protech = new DialogProperties();
			 protech.selection_mode = DialogConfigs.SINGLE_MODE;
			 protech.selection_type = DialogConfigs.DIR_SELECT;
			 protech.root = new File("/storage/emulated/0/");
			 protech.error_dir = new File("/storage/emulated/0/");
			 protech.offset = new File(DialogConfigs.DEFAULT_DIR);
			 protech.extensions = null;
			 protech.show_hidden_files = false;
			 FilePickerDialog dialog = new FilePickerDialog(MainActivity.this,protech);
			 dialog.setTitle("Select a Folder");
			 dialog.setDialogSelectionListener(new DialogSelectionListener() {
					 @Override
					 public void onSelectedFilePaths(String[] files) {
						 check2 = 2;

						 edittextDir.setText(Arrays.asList(files).get(0).toString());
						 dirData = Arrays.asList(files).get(0).toString();

					 }
				 });
			 dialog.show();

			 
			 
		 }
	 });
		
		//converte button onClick
		converte.setOnClickListener(new View.OnClickListener(){
			
		   @Override
		   public void onClick(View pl)
		   {
			   if(check == 0){
				   
				   Toast.makeText(getApplicationContext(),"please pick video file",Toast.LENGTH_SHORT).show();
				   
			   }else{
				   
				   if(check2 == 0){
					   
					   Toast.makeText(getApplicationContext(),"please choose a folder to save to",Toast.LENGTH_SHORT).show();
					   
				   }else{
				   //to replace mp4 with mp3
				   replaceData = Uri.parse(videoData.replace("mp4", "mp3")).getLastPathSegment();
				   
					   new AsyncTask<String, String, String>() {
						   @Override
						   protected void onPreExecute() {
							   super.onPreExecute();
							   //task preexecute
							   Toast.makeText(getApplicationContext(),"start Converting to Mp3",Toast.LENGTH_LONG).show();
							   progress("","Converting to Audio",true);

			
						   }
						   @Override
						   protected String doInBackground(String... arg0) {
							   try {
								   new ProtechConvertor().genVideoUsingMuxer(videoData,dirData+"/"+replaceData, -1, -1, true, false);
							   } catch (IOException e) {

								   Toast.makeText(getApplicationContext(),String.valueOf((e)),Toast.LENGTH_LONG).show();
							   }
							   return null;
						   }
						   @Override
						   protected void onPostExecute(String result) {
							   super.onPostExecute(result);
							   //task executed
							   progress("","",false);
							   Toast.makeText(getApplicationContext(),"Converted to mp3",Toast.LENGTH_LONG).show();
						   } 
					   }.execute();
					   
				   
					   
			   }
			 }
		   }
		});
		
        
    }
	//progress Dialog
	public void progress(String title,String msg,boolean show){

		if(show){
			if(prog == null){
				prog = new ProgressDialog(this);
				prog.setMax(100);
				prog.setIndeterminate(true);
				prog.setCancelable(false);
			}
			prog.setTitle(title);
			prog.setMessage(msg);
			prog.show();
		}
		else{
			if(prog != null){

				prog.dismiss();

			}

		}


	}
//Don't forget to subscribe our YouTube channel for more projet
	}

