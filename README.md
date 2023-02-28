# GPT-3 Async/Sync Flash Card Creation

### Description
This is a demo application using GPT-3 to create flash cards for a given topic. Each flash card answer will provide an analogy along with the answer to provide better understanding. This also demonstrates the difference between using synchronous and asynchronous calls for long-standing network calls, showing the duration of each call.

### Setup
In order for you to use this API, you will need an API key from Open AI in order to make calls to the GPT-3 API. You will need to create and account and retrieve the API key [here](https://platform.openai.com/account/api-keys). Place this key in your application.properties like so:

```properties
API-KEY=${API-KEY}
```

or add to your environment variables.

### Making the calls
You have the option of 2 endpoints.
* ***api/flash-cards/async*** --> This will call the GPT-3 API asynchronously as many times as there are technologies/questions. You will see that this is much faster as all the API calls are happening asynchronously.
* ***api/flash-cards/sync*** --> All API calls to GPT-3 will be called synchronously, one by one, waiting for each call to resolve before making the next API call.

Your request body should consist of these fields:
* **message**: A comma separated list of technologies or questions you would like answers for.
* **model**: One of the models you would like GPT-3 to use to answer the questions. Read the [GPT-3 Model Documentation](https://platform.openai.com/docs/models/gpt-3) for more info.
* **temp** & **top_p**: The temperature and top_p settings control how deterministic the model is in generating a response. If you're asking it for a response where there's only one right answer, then you'd want to set these lower. Values range from 0.0 to 1.0
* **max_tokens**: The tokens determine the max length of the response that will be returned. Read more to get a better understanding from the [Token Documentation](https://platform.openai.com/docs/introduction/tokens). Each model will accept a different max request of tokens. You can view the max tokens for each model in the [GPT-3 Model Documentation](https://platform.openai.com/docs/models/gpt-3).

Below is an example request body you can use in Postman. Play around with the settings if you choose.
```json
// Example json
"message": "Apache Kafka, Spring Boot",
"model": "text-davinci-003",
"temp": 1.0,
"max_tokens": 2000,
"top_p": 1.0
```

You will find that keeping the request body the same, and only changing the endpoints from ***async*** to ***sync*** or vice versa, the call duration will drastically change, demonstrating the power of making asynchronous calls.

### Reference Documentation

For further reference, please consider the following:

* [Open AI Documentation](https://platform.openai.com/docs/introduction/overview)


