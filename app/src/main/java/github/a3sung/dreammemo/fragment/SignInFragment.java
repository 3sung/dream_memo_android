package github.a3sung.dreammemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import github.a3sung.dreammemo.R;
import github.a3sung.dreammemo.adapter.MainPagerAdapter;

public class SignInFragment extends Fragment {

    EditText txtId, txtPw;
    Button btnSignIn;
    TextView btnSignUp;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SignInFragment newInstance(int sectionNumber) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtId = view.findViewById(R.id.txtId);
        txtPw = view.findViewById(R.id.txtPw);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainPagerAdapter inst = MainPagerAdapter.getInstance();
                if (inst != null){
                    inst.startFragment(1, SignUpFragment.newInstance(0), null);
                    System.out.println("test3");
                }
            }
        });
        System.out.println("test1");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
