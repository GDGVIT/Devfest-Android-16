package com.gdgvitvellore.devfest.Control.DataGenerator;

import com.gdgvitvellore.devfest.Entity.About.Fragments.AboutFragment;
import com.gdgvitvellore.devfest.Entity.About.Fragments.AboutFragment.Group;
import com.gdgvitvellore.devfest.Entity.Actors.Child;
import com.gdgvitvellore.devfest.gdgdevfest.R;

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
        group.setName("Out Patrons");
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
        group.setName("Our Sponsors");
        return group;
    }

    public static Group getContacts(AboutFragment aboutFragment){

        List<Child> childList=new ArrayList<>();
<<<<<<< HEAD
=======
        Child child1=new Child();
        child1.setName("Facebook");
        child1.setImageType(Child.IMAGE_RESOURCE);
        //child1.setImageResource(R.drawable.ic_facebook);
        child1.setImageResource(R.drawable.ic_gdg);
        child1.setDesignation("http://facebook.com/gdgvitvellore");

        Child child2=new Child();
        child2.setName("Website");
        child2.setImageType(Child.IMAGE_RESOURCE);
        //child2.setImageResource(R.drawable.ic_website);
        child2.setImageResource(R.drawable.ic_gdg);
        child2.setDesignation("http://gdgvitvellore.com/");

        Child child3=new Child();
        child3.setName("Google+");
        child3.setImageType(Child.IMAGE_RESOURCE);
        //child3.setImageResource(R.drawable.ic_gplus);
        child3.setImageResource(R.drawable.ic_gdg);
        child3.setDesignation("https://plus.google.com/+gdgvitvellore");

        Child child4=new Child();
        child4.setName("Mail");
        child4.setImageType(Child.IMAGE_RESOURCE);
        //child4.setImageResource(R.drawable.ic_mail);
        child4.setImageResource(R.drawable.ic_gdg);
        child4.setDesignation("gdgvitvellore@gmail.com");

        Child child5=new Child();
        child5.setName("CALL");
        child5.setImageType(Child.IMAGE_RESOURCE);
        //child5.setImageResource(R.drawable.ic_call);
        child5.setImageResource(R.drawable.ic_gdg);
        child5.setDesignation("+917708150636");

        Child child6=new Child();
        child6.setName("FIND US");
        child6.setImageType(Child.IMAGE_RESOURCE);
        //child6.setImageResource(R.drawable.ic_location);
        child6.setImageResource(R.drawable.ic_gdg);
        child6.setDesignation("http://facebook.com/gdgvitvellore");

        childList.add(child1);
        childList.add(child2);
        childList.add(child3);
        childList.add(child4);
        childList.add(child5);
        childList.add(child6);

>>>>>>> da08a59a4a7c832724a1458f683159a4145c2fc4
        Group group= aboutFragment.new Group(childList);
        group.setName("Contact Us");
        return group;
    }


}
