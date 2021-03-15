## Online Polling Application 
- Design and implement an application that is supposed to handle online polling. 

### Functional Requirements 

- Polling is on different topics and 
    - each polling has a question, 
    - bounded set of valid options that can be chosen for voting and 
    - time window during which polling can be carried out. 
- A polling is identified by a unique poll name. 
- A valid user can participate in online polling and 
    - can vote by choosing one of the options.
- Voting Status 
- Invalid Vote 

### Proposed Solution 

- Consume Events via REST API, File 
- Simulate Event Driven architecture using Sprint Event 
- Event Processor consume the Event and categorizes them into Valid and Invalid Events 
- Valid events are processed for Poll Result 
- Invalid events are processed by Invalid Event Processor along with reason of invalidation

### Notes to Interviewer
- I have made slight changes to the internals of the application, But overall the end to end flow is as per the requirement.
- We start with Data for 
    - Polling Events
    - Valid Polls 
    - Valid Users 
- Consume the Polling Events posted via REST API
    - We post the same JSON Payload "DataSetEvent" that is provided in the requirement.
- Post that we could post the Payloads for 
    - Valid Polls 
        - 'DataSetPolls' that is provided in the requirement.
    - Valid Users
        - 'DataSetUsers' that is provided in the requirement. 
- Also internally the processing is Event Driven using Spring Events 
- We have 2 Separate Streams for Data processing 
    - Valid Polls 
        - These eventually provide us the Voting Status/Result 
    - Invalid Polls 
        - These eventually provide us the Error Details like InvalidUser, InvalidTime, InvalidOption, DuplicateVote
- We have REST endpoints for Voting Status and Error Details as described below.
    - Currently we have only basic details as mentioned in Requirement document, this could be enhanced if needed
- Internally I have used H2 In-memory database to keep track of transient data, status     
- This app could be better written as Event Streams using Apache Flink, but that is for future enhancements
- In nutshell have taken few shortcuts due to time constraint, goal was functional service 
    - Taking inputs for Events, Polls, Users
    - Providing the Voting result status and Error Details
- Few enhancements that I could think of as of now are like,
    - Having a processor which would queue the legitimate Events which have arrived early, and post them into system when the time comes.
        - E.g. Id the event has arrived 1 hr early, we don't want to lose that if that is Valid, and keep it in Queue, to be posted after 1 hr.
    - Ability to reprocess the errored events after fixing it.
    - I would keep working on few ideas that I might get post submission ... Thank you.                 
                                                  

### High Level Design 
- ![](imgs/Online%20Polling.png) 

### Alternative Solutions 
- Apache Flink Event Streams [TODO : Provide more details] 
- Kafka / Active MQ based event processing [TODO : Provide more details] 
 

### Implementation 
- REST API Approach :: Postman Requests
    - Tech Stack Used 
    - Spring Events 
    - Spring Boots 
    - REST Controllers 
    - H2 Database for transient In-Memory database 
    - JPA/Hibernate 

### Code Documentation 
- EventController
    - API  
        - http://localhost:8083/postEvents
        - http://localhost:8083/events
    - Processes the Event posted via Postman 
    - Event Driven Approach 
    - EventController generates a Event PollingEvent and emits it 
- EventProcessor 
    - consumes the PollingEvent posted by EventController and then
    - Emits PollResultEvent
        - Event is Valid 
            - Display Aggregated Results as shown below   
        - Emits InvalidPollingEvent
        - If the Event in Invalid, we would later report it via Display 
            - Display count of Invalid Events - InvalidUser, InvalidTime, InvalidOption, DuplicateVote as explained below            


### REST API's

#### Posting Events 
- http://localhost:8083/postEvents

~~~json
[
  {
    "pollingname": "PollA",
    "useridentity": "user1",
    "eventtime": "2021-03-06 22:27:16.799",
    "useroption": "OptionA1"
  },
  {
    "pollingname": "PollC",
    "useridentity": "user1",
    "eventtime": "2021-03-06 22:27:16.800",
    "useroption": "InvalidOption"
  }
]  
~~~

#### Posting Polls 
- http://localhost:8083/postPolls

~~~json
[
	{
		"starttime": "2021-03-14 17:27:16.799",
		"endtime": "2021-03-14 17:27:16.799",
		"validoptions": "OptionA1, OptionA2, OptionA3, OptionA4",
		"pollingname": "PollA"
	},
	{
		"starttime": "2021-03-14 17:27:16.799",
		"endtime": "2021-03-14 17:27:16.799",
		"validoptions": "OptionB1, OptionB2, OptionB3, OptionB4",
		"pollingname": "PollB"
	}
]
~~~ 


#### Posting Users  
- http://localhost:8083/postUsers 
~~~json
[
	{
		"useridentity": "user1"
	},
	{
		"useridentity": "user2"
	},
	{
		"useridentity": "user3"
	}
]
~~~


#### View Results Aggregated  
- http://localhost:8083/resultaggregation
- ![](imgs/Poll%20Results.png) 

#### View Errors   
- http://localhost:8083/errors
- ![](imgs/Invalid%20Events%20Reason.png)  