package com.gdgvitvellore.devfest.Control.DataGenerator;

import com.gdgvitvellore.devfest.Entity.About.Fragments.AboutFragment;
import com.gdgvitvellore.devfest.Entity.About.Fragments.AboutFragment.Group;
import com.gdgvitvellore.devfest.Entity.Actors.Child;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prince Bansal Local on 10/14/2016.
 */


public class AboutDataGenerator {

    public static Group getPatrons(AboutFragment aboutFragment){

        List<Child> childList=new ArrayList<>();
        Child child1=new Child();
        child1.setName("Dr. G. Viswanathan");
        child1.setDesignation("Chancellor & Founder\n" +
                "VIT University");
        child1.setImageUrl("http://devfest.gdgvitvellore.com/images/gv.jpg");
        Child child2=new Child();
        child2.setName("Mr. Sankar Viswanathan");
        child2.setDesignation("Vice President\n" +
                "VIT University");
        child2.setImageUrl("http://devfest.gdgvitvellore.com/images/vpa_small.jpg");
        Child child3=new Child();
        child3.setName("Dr. Sekar Viswanathan");
        child3.setDesignation("Vice President\n" +
                "VIT University");
        child3.setImageUrl("http://devfest.gdgvitvellore.com/images/vpua_small.jpg");
        Child child4=new Child();
        child4.setName("Mr. G. V. Selvam");
        child4.setDesignation("Vice President\n" +
                "VIT University");
        child4.setImageUrl("http://devfest.gdgvitvellore.com/images/vpc_small.jpg");
        Child child5=new Child();
        child5.setName("Dr. Anand A. Samuel");
        child5.setDesignation("Vice Chancellor\n" +
                "VIT University");
        child5.setImageUrl("http://devfest.gdgvitvellore.com/images/anand.jpg");
        Child child6=new Child();
        child6.setName("Dr. V. Raju");
        child6.setDesignation("Pro-Vice Chancellor\n" +
                "VIT University");
        child6.setImageUrl("http://devfest.gdgvitvellore.com/images/v_raju.JPG");
        Child child7=new Child();
        child7.setName("Dr. S. Narayanan");
        child7.setDesignation("Pro-Vice Chancellor\n" +
                "VIT University");
        child7.setImageUrl("http://devfest.gdgvitvellore.com/images/provc.jpg");
        childList.add(child1);
        childList.add(child2);
        childList.add(child3);
        childList.add(child4);
        childList.add(child5);
        childList.add(child6);
        childList.add(child7);
        Group group= aboutFragment.new Group(childList);
        return group;
    }

    public static Group getSponsors(AboutFragment aboutFragment){

        List<Child> childList=new ArrayList<>();
        Child child1=new Child();
        child1.setName("Google Developers");
        child1.setImageUrl("http://devfest.gdgvitvellore.com/images/gdglogo.png");
        Child child2=new Child();
        child2.setName("Cardea Labs");
        child2.setImageUrl("http://devfest.gdgvitvellore.com/images/cardea-labs.png");
        Child child3=new Child();
        child3.setName("Mi Beat");
        child3.setImageUrl("http://devfest.gdgvitvellore.com/images/mibeat.png");
        Child child4=new Child();
        child4.setName("Chalk Street");
        child4.setImageUrl("http://devfest.gdgvitvellore.com/images/chalkstreet2.png");
        Child child5=new Child();
        child5.setName("Venturesity");
        child5.setImageUrl("http://devfest.gdgvitvellore.com/images/venturesity.png");
        childList.add(child1);
        childList.add(child2);
        childList.add(child3);
        childList.add(child4);
        childList.add(child5);
        Group group= aboutFragment.new Group(childList);
        return group;
    }

    public static Group getContacts(AboutFragment aboutFragment){

        List<Child> childList=new ArrayList<>();
        Group group= aboutFragment.new Group(childList);
        return group;
    }


}
