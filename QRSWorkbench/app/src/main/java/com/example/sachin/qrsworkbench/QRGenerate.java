package com.example.sachin.qrsworkbench;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRGenerate extends AppCompatActivity {
    public final static int QRcodeWidth = 500;
    Button button;
    String toWrite;
    Bitmap bitmap ;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerate);
        button = findViewById(R.id.button);
        imageView=findViewById(R.id.imageView);

         Intent intent=getIntent();
        toWrite=intent.getStringExtra("product_id").toString();


        try {
            bitmap = TextToImageEncode(toWrite);

            imageView.setImageBitmap(bitmap);


        } catch (WriterException e) {
            e.printStackTrace();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrintHelper photoPrinter = new PrintHelper(QRGenerate.this);
                photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                photoPrinter.printBitmap("droids.jpg - test print", bitmap);
            }
        });
    }
    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.Black):getResources().getColor(R.color.White);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}

