<<<<<<< HEAD
package com.example.pepe.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.pepe.data.LoginDataSource;
import com.example.pepe.data.LoginRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
/*
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(LoginRepository.getInstance(new LoginDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }


}

 */
=======
//package com.example.pepe.ui.login;
//
//import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.annotation.NonNull;
//
//import com.example.pepe.data.LoginDataSource;
//import com.example.pepe.data.LoginRepository;
//
///**
// * ViewModel provider factory to instantiate LoginViewModel.
// * Required given LoginViewModel has a non-empty constructor
// */
//public class LoginViewModelFactory implements ViewModelProvider.Factory {
//
//    @NonNull
//    @Override
//    @SuppressWarnings("unchecked")
//    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
//            return (T) new LoginViewModel(LoginRepository.getInstance(new LoginDataSource()));
//        } else {
//            throw new IllegalArgumentException("Unknown ViewModel class");
//        }
//    }
//}
>>>>>>> 15861cb5c1b17dc703d7ad19b2b4f4a37ce6c4ce
