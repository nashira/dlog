# Dlog - Data Capture, Logging and Visualization

<img width="1157" alt="Screen Shot" src="https://github.com/nashira/dlog/assets/757096/93515212-5c46-4eee-b875-ffbb80798db6">

This project was started because I found myself repeatedly creating one-off solutions for capturing and visualizing data.  Most projects were not demanding enough to dedicate resources to a more robust system until I started roasting coffee at home.  Like many home roasters, I used an open-source roast logging software to capture data for real-time visualization during roasting and for offline analysis and planning.  As I customized my roaster more I wanted more control over my data capture software, too.

This software is in an early development phase and subject to lots of change.  

## Problem Space and Product Goals

The initial use-cases I'm trying to address are: 
 1) running the data capture software on a laptop, desktop, tablet or mobile device
 2) capturing relatively small amounts of data that fit into the devices' memory
 3) providing real-time visualization of the data
 4) saving the raw data in easy to use formats

Dlog accomplishes these tasks by providing a pipeline of data processing, with 3 configurable stages. 

The first stage is the client stage where you define the data source and any parameters for connecting to it and configuring it.  All clients transform data from it's raw, protocol-dependent format to a stream of DataFrames.  DataFrames are a generalization for representing arbitrary data as a map of keys to typed values.

The second stage is where you define how to transform DataFrames into TimeSeries and Events, which are the components of a DataCapture.  A DataCapture is what gets serialized for saving and exporting.  Any 'import' functionality will convert data to the DataCapture format.

The third stage is where you define how to render a DataCapture.  It specifies which data to use, how to format it, etc.

Breaking the problem up into steps, each with its own abstractions, comes with many benefits.  Flexibility, extensibility, reusability, etc.


## Software Architecture

Dlog is built on Kotlin Multiplatform.  My first priority is desktop, but I would like to add support for mobile once the core features are implemented and stable.

The app uses a layered architecture aka clean architecture aka ports-and-adapters.

At the bottom is the Data layer, it handles persistence and external communication.  It is the interface to local and remote data sources; it provides the 'ports' to external depencies.

In the middle is the Domain layer.  In Dlog that is comprised of Repositories and Use Cases.  Repositories provide an interface for app state via domain models. Repositories are observable, single sources of truth and provide lower-level read and write APIs.  Use-cases implement atomic logic operations.  They are stateless and can be used safely from any feature or other use-cases.  They encapsulate a set of lower-level calls into a reusable function. 

The top layer is the Presentation layer (ViewModels and UI).  ViewModels provide mapping between use-cases and UI.  They recieve flows of data from use-cases and transform it to UI State.  In the other direction, they handle UI events and map them to commands that are passed off to use-cases, typically to update state.  UI is declarative and consumes State flows passed from ViewModels to determine what is rendered.  UI passes all events to ViewModels as Intents.

Each major layer operates within its own data models and abstractions.  At the boundary of each layer data is transformed.  Between the Data and Domain layers, data is transformed between wire/serialized representations and domain models.  Between the Domain and Presentation layers, data is transformed between domain models and UI state/intents.


