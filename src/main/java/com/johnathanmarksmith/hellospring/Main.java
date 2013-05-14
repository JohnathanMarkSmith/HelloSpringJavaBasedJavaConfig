package com.johnathanmarksmith.hellospring;


import com.johnathanmarksmith.hellospring.bean.HelloWorld;
import com.johnathanmarksmith.hellospring.model.Message;
import com.johnathanmarksmith.hellospring.service.MessageService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.apache.log4j.Logger.getLogger;


/**
 * Date:   4/29/13 / 8:46 AM
 * Author: Johnathan Mark Smith
 * Email:  john@johnathanmarksmith.com
 * <p/>
 * Comments:
 *
 *    This is just a example on how to use Spring, Maven, JavaConfig
 */


public class Main
{

    private static final Logger LOGGER = getLogger(Main.class);

    public static void main(String[] args)
    {
        // in this setup, both the main(String[]) method and the JUnit method both specify that
        ApplicationContext context = new AnnotationConfigApplicationContext( HelloWorldConfiguration.class );
        MessageService mService = context.getBean(MessageService.class);
        HelloWorld helloWorld = context.getBean(HelloWorld.class);

        /**
         * Displaying default messgae
         */
        LOGGER.debug("Message from HelloWorld Bean: " + helloWorld.getMessage());

        /**
         *   Saving Message to database
         */
        Message message = new Message();
        message.setMessage(helloWorld.getMessage());
        mService.SaveMessage(message);

        /**
         * Settting new message in bean
         */
        helloWorld.setMessage("I am in Staten Island, New York");
        LOGGER.debug("Message from HelloWorld Bean: " + helloWorld.getMessage());

        /**
         * Saving Message in database.
         */
        message.setMessage(helloWorld.getMessage());
        mService.SaveMessage(message);

        /**
         * Getting messages from database
         *    - display number of message(s)
         *    - display each message in database
         */
        List<Message> myList = mService.listMessages();
        LOGGER.debug("You Have " + myList.size() + " Message(s) In The Database");

        for (Message i : myList)
        {
            LOGGER.debug("Message: ID: " + i.getId() + ", Message: " + i.getMessage() + ".");
        }
    }
}
