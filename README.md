### HelloSpringJavaBasedJavaConfig

This is a sample Spring Project to show how to use JavaConfig and not XML files. This example also will show how to use @PropertySource for reading properties and using the Environment Object to add properties to your objects.

### How to use Spring JavaConfig and not XML files for configuation

Consider replacing Spring XML configuration with JavaConfig

Using Spring XML configuration is so 2000’s the time has come to push the XML away and look at JavaConfig.

Here is the main code to my sample project

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
 

You can see from this code that the ApplicationContext is mapped to a HelloWorldConfig.class file its not using a XML file. Below is the code to the HelloWorldConfiguration.class.

	@Configuration
	@Import(DatabaseConfiguration.class)
	@ComponentScan
	@PropertySource("classpath:application.properties")
	public class HelloWorldConfiguration {

	    @Bean
	    public HelloWorld getHelloWorld(Environment env) {
		HelloWorld hw = new HelloWorld();
		hw.setMessage(env.getProperty("bean.text"));
		return hw;
	    }
	}


Now lets take a look at how I setup the database in JavaConfig and not in a XML file.

    @Configuration
    @EnableTransactionManagement
    @ComponentScan(basePackageClasses = {HelloWorld.class})
    @PropertySource("classpath:application.properties")
    public class DatabaseConfiguration
    {


    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/schema.sql"));

            DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
            dataSourceInitializer.setDataSource(dataSource);
            dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
            return dataSourceInitializer;
        }

        @Bean
        public DataSource hsqlDataSource() {
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setDriverClassName(org.hsqldb.jdbcDriver.class.getName());
            basicDataSource.setUsername("sa");
            basicDataSource.setPassword("");
            basicDataSource.setUrl("jdbc:hsqldb:mem:mydb");
            return basicDataSource;
        }

        @Bean
        public LocalSessionFactoryBean sessionFactory(Environment environment,
                                                  DataSource dataSource) {

            String packageOfModelBeans = Message.class.getPackage().getName();
            LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
            factoryBean.setDataSource(dataSource);
            factoryBean.setHibernateProperties(buildHibernateProperties(environment));
            factoryBean.setPackagesToScan(packageOfModelBeans);
            return factoryBean;
        }

        protected Properties buildHibernateProperties(Environment env) {
            Properties hibernateProperties = new Properties();

            hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
            hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
            hibernateProperties.setProperty("hibernate.use_sql_comments", env.getProperty("hibernate.use_sql_comments"));
            hibernateProperties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
            hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

            hibernateProperties.setProperty("hibernate.generate_statistics", env.getProperty("hibernate.generate_statistics"));

            hibernateProperties.setProperty("javax.persistence.validation.mode", env.getProperty("javax.persistence.validation.mode"));

            //Audit History flags
            hibernateProperties.setProperty("org.hibernate.envers.store_data_at_delete", env.getProperty("org.hibernate.envers.store_data_at_delete"));
            hibernateProperties.setProperty("org.hibernate.envers.global_with_modified_flag", env.getProperty("org.hibernate.envers.global_with_modified_flag"));

            return hibernateProperties;
        }

        @Bean
        public HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
            return new HibernateTransactionManager(sessionFactory);
        }


    }



You can see how easy it is to use JavaConfig and Not XML.. The time of using XML files with Springs is over...

checkout the project from github.

    mvn packge
    cd target
    java -jar HelloSpringJavaBasedJavaConfig.jar

Thats it and you should see the following line on the console:

    2013-04-29 08:49:36,828 [main] DEBUG com.johnathanmarksmith.hellospring.Main - Message from HelloWorld Bean: I love New York.
    2013-04-29 08:49:37,044 [main] DEBUG com.johnathanmarksmith.hellospring.Main - Message from HelloWorld Bean: I am in Staten Island, New York
    2013-04-29 08:49:37,091 [main] DEBUG com.johnathanmarksmith.hellospring.Main - You Have 2 Message(s) In The Database
    2013-04-29 08:49:37,091 [main] DEBUG com.johnathanmarksmith.hellospring.Main - Message: ID: 1, Message: I love New York..
    2013-04-29 08:49:37,091 [main] DEBUG com.johnathanmarksmith.hellospring.Main - Message: ID: 2, Message: I am in Staten Island, New York.

This Project is using Java, Spring, Hibernate, Maven, jUnit, Log4J, HSQLDB and Github.

If you have any questions please email me at john@johnathanmarksmith.com