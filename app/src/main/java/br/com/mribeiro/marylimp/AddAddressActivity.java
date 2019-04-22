package br.com.mribeiro.marylimp;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.stepstone.stepper.StepperLayout;

public class AddAddressActivity extends AppCompatActivity {
    ConstraintLayout firstPage, secondPage, thirdPage, fourthPage, fifthPage, sixthPage;
    Button firstNextButton, secondNextButton;
    Button firstBackButton, secondBackButton, thirdBackButton , fouthBackButton, fifthBackButton;

    //SecondPageVariables
    Integer tipoDeImovel;

    //ThirdPageVariables
    Integer metragemDoImovel;

    //FourthPageVariables
    Integer quantidadeDeBanheiros;

    //FifthPageVariables
    String cepDoImovel, logradouroDoImovel, numeroDoImovel, complementoDoImovel, bairroDoImovel, cidadeDoImovel, estadoDoImovel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        setupViewVariables();
    }

    public void goToPage(int page) {
        hideAllPages();
        showPage(page);
    }

    private void hideAllPages() {
        firstPage.setVisibility(View.GONE);
        secondPage.setVisibility(View.GONE);
        thirdPage.setVisibility(View.GONE);
        fourthPage.setVisibility(View.GONE);
        fifthPage.setVisibility(View.GONE);
        sixthPage.setVisibility(View.GONE);
    }

    private void showPage(int page) {
        switch (page) {
            case 1:
                firstPage.setVisibility(View.VISIBLE);
                break;
            case 2:
                secondPage.setVisibility(View.VISIBLE);
                break;
            case 3:
                thirdPage.setVisibility(View.VISIBLE);
                break;
            case 4:
                fourthPage.setVisibility(View.VISIBLE);
                break;
            case 5:
                fifthPage.setVisibility(View.VISIBLE);
                break;
            case 6:
                sixthPage.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setupViewVariables() {
        firstPage = findViewById(R.id.addAddressFirstPage);
        secondPage = findViewById(R.id.addAddressSecondPage);
        thirdPage = findViewById(R.id.addAddressThirdPage);
        fourthPage = findViewById(R.id.addAddressFourthPage);
        fifthPage = findViewById(R.id.addAddressFithPage);
        sixthPage = findViewById(R.id.addAddressSixthPage);

        firstBackButton = findViewById(R.id.firstBackButton);
        firstBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firstNextButton = findViewById(R.id.firstNextButton);
        firstNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(2);
            }
        });

        secondBackButton = findViewById(R.id.secondBackButton);
        secondBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(1);
            }
        });

        secondNextButton = findViewById(R.id.secondNextButton);
        secondNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(3);
            }
        });

        thirdBackButton = findViewById(R.id.thirdBackButton);
        thirdBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(2);
            }
        });

        fouthBackButton = findViewById(R.id.fourthPageBackButton);
        fouthBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(3);
            }
        });

        fifthBackButton = findViewById(R.id.fifthPageBackButton);
        fifthBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(4);
            }
        });

        //Sets up second page

        Button residenciabutton, escritorioButton, outrosButton;
        residenciabutton = findViewById(R.id.secondPageResidenciaButton);
        escritorioButton = findViewById(R.id.secondPageEscritorioButton);
        outrosButton = findViewById(R.id.secondPageOthersButton);
        residenciabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoDeImovel = 0;
                if (canGoToPage(3)) {
                    goToPage(3);
                } else {

                }
            }
        });
        escritorioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoDeImovel = 1;
                if (canGoToPage(3)) {
                    goToPage(3);
                } else {

                }
            }
        });
        outrosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 15/04/19 Add function to contact

            }
        });

        //Sets up third Page

        final TextInputLayout metragemTextInputLayout;
        SeekBar metragemSeekBar;
        metragemTextInputLayout = findViewById(R.id.thirdPageMetragemTextInput);
        metragemSeekBar = findViewById(R.id.thirdPageMetragemSeekBar);
        metragemSeekBar.setMax(300);
        metragemSeekBar.setVisibility(View.GONE);
        metragemSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                metragemDoImovel = progress;
                metragemTextInputLayout.getEditText().setText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Button thirdPageNextButton = findViewById(R.id.thirdPageNextButton);
        thirdPageNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metragemDoImovel = Integer.valueOf(metragemTextInputLayout.getEditText().getText().toString());
                if (canGoToPage(4)) {
                    goToPage(4);
                } else {
                    Toast.makeText(getApplicationContext(), "Você precisa inserir uma metragem válida para prosseguir", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Sets up fourth Page

        Button oneButton, twoButton, threeButton, fourButton;
        oneButton = findViewById(R.id.fourthPageOneButton);
        twoButton = findViewById(R.id.fourthPageTwoButton);
        threeButton = findViewById(R.id.fourthPageThirdButton);
        fourButton = findViewById(R.id.fourthPageFourPlusButton);
        oneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidadeDeBanheiros = 1;
                if (canGoToPage(5)) {
                    goToPage(5);
                } else {

                }
            }
        });
        twoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidadeDeBanheiros = 2;
                if (canGoToPage(5)) {
                    goToPage(5);
                } else {
                    Toast.makeText(getApplicationContext(), "Você precisa preencher todos os dados obrigatórios corretamente para prosseguir", Toast.LENGTH_LONG).show();
                }

            }
        });
        threeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidadeDeBanheiros = 3;
                if (canGoToPage(5)) {
                    goToPage(5);
                } else {
                    Toast.makeText(getApplicationContext(), "Você precisa preencher todos os dados obrigatórios corretamente para prosseguir", Toast.LENGTH_LONG).show();
                }
            }
        });
        fourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantidadeDeBanheiros = 4;
                if (canGoToPage(5)) {
                    goToPage(5);
                } else {
                    Toast.makeText(getApplicationContext(), "Você precisa preencher todos os dados obrigatórios corretamente para prosseguir", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button fourthBackButton;
        fourthBackButton = findViewById(R.id.fourthPageBackButton);
        fourthBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(3);
            }
        });

        //Sets up fifth page

        Button fifthBackButton;
        fifthBackButton = findViewById(R.id.fifthPageBackButton);
        fifthBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPage(4);
            }
        });

        hideAllPages();
        showPage(1);



    }

    private boolean canGoToPage(int page) {
        switch (page) {
            case 1:
                return true;

            case 2:
                return true;

            case 3:
                if (tipoDeImovel == null || tipoDeImovel < 0 || tipoDeImovel > 2) {
                    return false;
                } else {
                    return true;
                }
            case 4:
                if (metragemDoImovel == null || metragemDoImovel < 20 || metragemDoImovel > 300 ) {
                    return false;
                } else {
                    return true;
                }
            case 5:
                if (quantidadeDeBanheiros == null || quantidadeDeBanheiros < 1 || quantidadeDeBanheiros > 4) {
                    return false;
                } else {
                    return true;
                }
            case 6:
                if (cepDoImovel == null || cepDoImovel == "" || logradouroDoImovel == null || logradouroDoImovel == "" || bairroDoImovel == null || bairroDoImovel == "" || estadoDoImovel == null || estadoDoImovel == "" || cidadeDoImovel == null || cidadeDoImovel == "") {
                    return false;
                } else {
                    return true;
                }

        }
        return false;
    }
}
