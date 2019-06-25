package com.example.afinal.float_button;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.afinal.R;

public class Addone extends AppCompatActivity {
    private ImageView iventrory, rquirement, idustrial;
    private ImageView cmmerical, sle, lese, boking, rsidential;
    private Button nextButton;
    String list, property_type, property_for;
    private TextView txtnext1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addone);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chhatt (چھت)");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iventrory = (ImageView) findViewById(R.id.Inventory);
        //rquirement = (ImageView) findViewById(R.id.Requirement);
        idustrial = (ImageView) findViewById(R.id.industrial);
        rsidential = (ImageView) findViewById(R.id.Residential);
        cmmerical = (ImageView) findViewById(R.id.commerical);
       /* final Spinner cat=(Spinner) findViewById(R.id.category_spinner);*/
        sle = (ImageView) findViewById(R.id.sale);
        lese = (ImageView) findViewById(R.id.lease);
        boking = (ImageView) findViewById(R.id.booking);
        nextButton = (Button) findViewById(R.id.next_button);

        /*nextButton.setText(setVisible(View.GONE));*/
        iventrory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iventrory.setImageResource(R.drawable.inventory1);
                //rquirement.setImageResource(R.drawable.requirement);
                sle.setImageResource(R.drawable.sale);
                list = "Inventrory";

            }
        });


        rquirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rquirement.setImageResource(R.drawable.requirement1);
                iventrory.setImageResource(R.drawable.inventory);
                sle.setImageResource(R.drawable.buy);
               /* hme.setText("Residential");*/
                list = "Requirement";
            }
        });

            rsidential.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rsidential.setImageResource(R.drawable.residential1);
                    cmmerical.setImageResource(R.drawable.commercial);
                    idustrial.setImageResource(R.drawable.industrial);
                    property_type = "Residential";
                }
            });
            cmmerical.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rsidential.setImageResource(R.drawable.residential);
                    cmmerical.setImageResource(R.drawable.commercial1);
                    idustrial.setImageResource(R.drawable.industrial);
                    property_type = "Commercial";
                }
            });
            idustrial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rsidential.setImageResource(R.drawable.residential);
                    cmmerical.setImageResource(R.drawable.commercial);
                    idustrial.setImageResource(R.drawable.industrial1);
                    property_type = "Industrial";
                }
            });
                sle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sle.setImageResource(R.drawable.sale1);
                        sle.setImageResource(R.drawable.buy1);
                        lese.setImageResource(R.drawable.rent);
                        boking.setImageResource(R.drawable.booking);
                        property_for = "Sale";
                        nextButton.setText("Next");
                        //;
                    }
                });
            lese.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sle.setImageResource(R.drawable.sale);
                    lese.setImageResource(R.drawable.rent1);
                    boking.setImageResource(R.drawable.booking);
                    property_for = "Rent";
                    nextButton.setText("Next");
                }
            });
            boking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sle.setImageResource(R.drawable.sale);
                    lese.setImageResource(R.drawable.rent);
                    boking.setImageResource(R.drawable.booking1);
                    property_for = "Booking";
                    nextButton.setText("Next");
                }
            });
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String list1 = list;
                        String propertytype = property_type;
                        String propertyfor = property_for;
                       /* if (list.equals("Inventrory") || list.equals("Requirement")||
                                property_type.equals("Residential") || property_type.equals("Commercial") || property_type.equals("Industrial")
                                ||property_for.equals("Sale") || property_for.equals("Rent") || property_for.equals("Booking")) {*/
                                    Intent addListingTwo = new Intent(Addone.this, Addtwo.class);
                                    addListingTwo.putExtra("list1", list1);
                                    addListingTwo.putExtra("propertytype", propertytype);
                                    addListingTwo.putExtra("propertyfor", propertyfor);
                                    startActivity(addListingTwo);
                            /*    }
                            else {
                                        Toast.makeText(Addone.this,"Please select the item",Toast.LENGTH_SHORT).show();
                        }*/
                    }

                });

}
}