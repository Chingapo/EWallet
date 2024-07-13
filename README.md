<a id="readme-top"></a>

[![LinkedIn][linkedin-shield]][linkedin-url]




<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#getting-started">Getting Started</a></li>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project
The project is a fully functional backend for an e-wallet application built using Java and Spring Boot in the IntelliJ IDE. It leverages a microservice architecture managed with Netflix Eureka. The services communicate via REST APIs and use Apache Kafka for event management, ensuring tasks are handled efficiently without user intervention. Additionally, the system sends transactional emails to both sender and receiver upon successful transactions.

Its key features are :

* Microservice Architecture: Modular services that can be independently developed and deployed.

* RESTful Communication: Efficient API communication between services and clients.

* Event Management with Kafka: Asynchronous handling of events to enhance performance and scalability.

* Email Notifications: Automatic email notifications for transaction confirmations.


<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

To load the full functionality of this backend on your local system go through the following steps.



### Prerequisites

These are the softwares or applications you would need to have installed already before you can clone and run this project.
* JDK-17.
* Intellij or any other IDE.
* Apache Kafka. (add it to path variable and configure it to run on port 9092)
* Postman.




### Installation

After the above requirements are installed.

1. Clone the repo into a desired folder
   ```sh
   git clone https://github.com/Chingapo/EWallet
   ```
3. Ensure that zookeeper and Kafka are up and running. Use the following steps:
   1. Open cmd
   2. navigate to \bin\windows of the kafka download.
      ```sh
      cd {Path-to-your-kafka-installation}
      ```
   3. Start zookeeper server using :
      ```sh
      .\zookeeper-server-start.bat {Path-to-your-kafka-installation}\config\zookeeper.properties
      ```
   4. In a different cmd window navigate to \bin\windows again and start the kafka server:
      ```sh
      cd {Path-to-your-kafka-installation}
      ```
      ```sh
      .\kafka-server-start.bat {Path-to-your-kafka-installation}\config\server.properties
      ```
   5. Create the following topics:
      1. USER_CREATED
      2. USER_DELETED
      3. notificationTopic
      
      To create a topic open another cmd prompt and navigate to Kafka downloads folder:
      ```sh
      cd {Path-to-your-kafka-installation}
      ```
      ```
      .\kafka-topics.bat --bootstrap-server=localhost:9092 --topic TOPIC_NAME --create
      ```  
   6. To check the list of topics created navigate to the \bin\windows directory and run:
      ```
      .\kafka-topics.bat --bootstrap-server=localhost:9092 --list
      ```
3. update the application.properties of each microservice configuring it to your own databases.
4. Open all microservices in different tabs of an IDE and load their maven dependencies.
5. Run all the microservices starting with serviceDiscovery followed by others.
6. Refer controllers of each microservice and use Postman to send requests and recieve responses

For any issues in setting up or installation, you can ping me on my contact details shared below.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Major functionality which can be checked:

1. CRUD operations on user. (where wallet for a user is created using kafka event)
2. Transferring money from one account to another.
3. Depositing money into an account
4. Withdrawing money into an account.
5. Exception handling when wrong or invalid credentials are passed.
6. Both the email-ids of the person in the transaction receive the email. (I'm keeping my email active for now if u want u can let the JavaMailSender be configured to my email and password.) 



<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If anyone wishes to collaborate with me for building a frontend for this functional backend, just ping me using my contact details below.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Arjun Pareek - [@Twitter_Handle](https://x.com/chxngapo) - arjunpareek03@gmail.com

Project Link: [https://github.com/Chingapo/EWallet](https://github.com/Chingapo/EWallet)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/chingapo
