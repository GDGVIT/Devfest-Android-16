package com.gdgvitvellore.devfest.Control.Customs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Prince Bansal Local on 10/13/2016.
 */

public class FaqFragmentManager extends FragmentManager {

    @Override
    public FragmentTransaction beginTransaction() {
        return null;
    }

    @Override
    public boolean executePendingTransactions() {
        return false;
    }

    @Override
    public Fragment findFragmentById(int i) {
        return null;
    }

    @Override
    public Fragment findFragmentByTag(String s) {
        return null;
    }

    @Override
    public void popBackStack() {

    }

    @Override
    public boolean popBackStackImmediate() {
        return false;
    }

    @Override
    public void popBackStack(String s, int i) {

    }

    @Override
    public boolean popBackStackImmediate(String s, int i) {
        return false;
    }

    @Override
    public void popBackStack(int i, int i1) {

    }

    @Override
    public boolean popBackStackImmediate(int i, int i1) {
        return false;
    }

    @Override
    public int getBackStackEntryCount() {
        return 0;
    }

    @Override
    public BackStackEntry getBackStackEntryAt(int i) {
        return null;
    }

    @Override
    public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {

    }

    @Override
    public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {

    }

    @Override
    public void putFragment(Bundle bundle, String s, Fragment fragment) {

    }

    @Override
    public Fragment getFragment(Bundle bundle, String s) {
        return null;
    }

    @Override
    public List<Fragment> getFragments() {
        return null;
    }

    @Override
    public Fragment.SavedState saveFragmentInstanceState(Fragment fragment) {
        return null;
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public void dump(String s, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strings) {

    }
}
