# GraphQL Spring Boot App

It exposes GraphQL APIs for Employee, Book, and Hospital modules, and it also emits employee subscription events when employee data is created, updated, or deleted.

## What is GraphQL

GraphQL is an API style where the client sends one request to a single GraphQL endpoint and asks only for the fields it needs.
In this project, the schema defines the available data and operations for Employee, Book, and Hospital features.

## Understanding the fundamentals

GraphQL is built around a few core ideas: types, schema, queries, mutations, subscriptions, and resolvers.
The schema tells the client what can be requested, and the resolver code returns the actual data for that request.

## Single endpoint and query-based fetching

A GraphQL application usually exposes a single endpoint such as `/graphql`, and the client chooses which fields to fetch in each request
That means the same endpoint can serve employee data, book data, and hospital data depending on the query body sent by the client

## Scalar and non-scalar types

### Scalar types

Scalar types are simple values such as `ID`, `String`, `Int`, `Float`, and `Boolean`.
From your schema, `Employee.id` uses `ID`, `Employee.name` uses `String`, and `Patient.age` uses `Int`.

Example:

```graphql
type Employee {
  id: ID!
  name: String!
  email: String!
  department: String!
}
```

```graphql
type Patient {
  id: ID!
  name: String!
  age: Int!
}
```

### Non-scalar types

Non-scalar types are complex values such as objects and lists.
From your schema, `Author`, `Book`, `Patient`, `Appointment`, `Doctor`, `Prescription`, and `Medicine` are object types, and `[Appointment!]!` and `[Medicine!]!` are list types.

Example:

```graphql
type Book {
  id: ID!
  title: String!
  authorId: ID!
  author: Author
}
```

```graphql
type Patient {
  id: ID!
  name: String!
  age: Int!
  appointments: [Appointment!]!
}
```

## Types and files

In this project, the schema is split across multiple `.graphqls` files: `employee.graphqls`, `book.graphqls`, and `hospital.graphqls`.
This makes the project easier to manage because each domain keeps its own types and operations in a separate file.

## Schema and resolvers

The **schema** defines the contract: types, queries, mutations, and subscriptions.
The **resolvers** are the Spring controller and service methods that execute the business logic and return data, such as `employees()`, `employeeById()`, `createEmployee()`, `updateEmployeeById()`, and `deleteEmployee()` in `EmployeeController` and `EmployeeService`.[4][2]

## Static and dynamic data

Static data means data already loaded in memory when the application starts, while dynamic data means data created or changed during runtime.
In your employee module, the initial employee list with Ram, Sita, and Arjun is static startup data, and records created or updated through GraphQL mutations are dynamic data.[2]

## Query

A **query** is used to read data from the server.
Your schema contains employee queries, book queries, and hospital queries.

### Employee query example

```graphql
query GetEmployees {
  employees {
    id
    name
    email
    department
  }
}
```

Postman body:

```json
{
  "query": "query GetEmployees { employees { id name email department } }"
}
```

### Employee by ID example

```graphql
query GetEmployeeById($id: ID!) {
  employeeById(id: $id) {
    id
    name
    email
    department
  }
}
```

Postman body:

```json
{
  "query": "query GetEmployeeById($id: ID!) { employeeById(id: $id) { id name email department } }",
  "variables": {
    "id": "1"
  }
}
```

### Book query example

```graphql
query GetBook($id: ID!) {
  bookById(id: $id) {
    id
    title
    authorId
    author {
      id
      name
    }
  }
}
```

Postman body:

```json
{
  "query": "query GetBook($id: ID!) { bookById(id: $id) { id title authorId author { id name } } }",
  "variables": {
    "id": "1"
  }
}
```

### Hospital patient query example

```graphql
query GetPatient($id: ID!) {
  patientById(id: $id) {
    id
    name
    age
    appointments {
      id
      visitDate
      reason
      doctor {
        id
        name
        specialization
      }
    }
  }
}
```

Postman body:

```json
{
  "query": "query GetPatient($id: ID!) { patientById(id: $id) { id name age appointments { id visitDate reason doctor { id name specialization } } } }",
  "variables": {
    "id": "1"
  }
}
```

### Hospital prescription query example

```graphql
query GetPrescription($id: ID!) {
  prescriptionById(id: $id) {
    id
    issuedDate
    patient {
      id
      name
    }
    doctor {
      id
      name
      specialization
    }
    medicines {
      id
      name
      dosage
    }
  }
}
```

Postman body:

```json
{
  "query": "query GetPrescription($id: ID!) { prescriptionById(id: $id) { id issuedDate patient { id name } doctor { id name specialization } medicines { id name dosage } } }",
  "variables": {
    "id": "1"
  }
}
```

## Mutation

A **mutation** is used to create, update, or delete data.
In your employee module, mutations call service methods that change the employee list and then publish events for subscriptions.

### Create employee

```graphql
mutation CreateEmployee($id: ID!, $name: String!, $email: String!, $department: String!) {
  createEmployee(id: $id, name: $name, email: $email, department: $department) {
    id
    name
    email
    department
  }
}
```

Postman body:

```json
{
  "query": "mutation CreateEmployee($id: ID!, $name: String!, $email: String!, $department: String!) { createEmployee(id: $id, name: $name, email: $email, department: $department) { id name email department } }",
  "variables": {
    "id": "10",
    "name": "Kumar",
    "email": "kumar@gmail.com",
    "department": "IT"
  }
}
```

### Update employee

```graphql
mutation UpdateEmployee($id: ID!, $name: String, $email: String, $department: String) {
  updateEmployeeById(id: $id, name: $name, email: $email, department: $department) {
    id
    name
    email
    department
  }
}
```

Postman body:

```json
{
  "query": "mutation UpdateEmployee($id: ID!, $name: String, $email: String, $department: String) { updateEmployeeById(id: $id, name: $name, email: $email, department: $department) { id name email department } }",
  "variables": {
    "id": "1",
    "name": "Ram Kumar",
    "email": "ramkumar@gmail.com",
    "department": "Architecture"
  }
}
```

### Delete employee

```graphql
mutation DeleteEmployee($id: ID!) {
  deleteEmployee(id: $id)
}
```

Postman body:

```json
{
  "query": "mutation DeleteEmployee($id: ID!) { deleteEmployee(id: $id) }",
  "variables": {
    "id": "1"
  }
}
```

## Subscription

A **subscription** is used to receive live data when the server emits events.
Your employee schema defines `employeeCreated`, `employeeUpdated`, and `employeeDeleted`, and the controller maps them to Flux streams from `EmployeeEventPublisher`.

### Subscription examples

```graphql
subscription EmployeeCreated {
  employeeCreated {
    id
    name
    email
    department
  }
}
```

```graphql
subscription EmployeeUpdated {
  employeeUpdated {
    id
    name
    email
    department
  }
}
```

```graphql
subscription EmployeeDeleted {
  employeeDeleted {
    id
    name
    email
    department
  }
}
```

Subscriptions are usually tested with a GraphQL client that supports WebSocket subscriptions rather than plain Postman request-response mode.

## How nested relations are handled

Nested relations mean one type contains another related type.
Your Book and Hospital schemas show this clearly with `Book.author`, `Patient.appointments.doctor`, and `Prescription.medicines`.

Example nested query:

```graphql
query GetBook($id: ID!) {
  bookById(id: $id) {
    id
    title
    author {
      id
      name
    }
  }
}
```

Another nested query:

```graphql
query GetPatient($id: ID!) {
  patientById(id: $id) {
    id
    name
    appointments {
      id
      visitDate
      doctor {
        id
        name
      }
    }
  }
}
```

## Aliases

Aliases let you rename fields in the response.
This is useful when the same query field is used more than once in one request.

Example:

```graphql
query EmployeeAliases {
  firstEmployee: employeeById(id: "1") {
    id
    name
  }
  secondEmployee: employeeById(id: "2") {
    id
    name
  }
}
```

Postman body:

```json
{
  "query": "query EmployeeAliases { firstEmployee: employeeById(id: \"1\") { id name } secondEmployee: employeeById(id: \"2\") { id name } }"
}
```

## Fragments

Fragments are reusable field groups.[20][5]
They help reduce repetition in queries, mutations, and subscriptions.

Example:

```graphql
fragment EmployeeFields on Employee {
  id
  name
  email
  department
}

query GetEmployees {
  employees {
    ...EmployeeFields
  }
}
```

Postman body:

```json
{
  "query": "fragment EmployeeFields on Employee { id name email department } query GetEmployees { employees { ...EmployeeFields } }"
}
```

## Project flow

- Main GraphQL app: `https://github.com/venkatram1999/graphql-springboot`
- Client subscription app: `https://github.com/venkatram1999/graphql-subscription-client` 
- Main app emits employee events after create, update, and delete operations.
- Client app subscribes to those events and consumes them.
