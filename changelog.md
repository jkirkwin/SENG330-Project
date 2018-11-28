# Changes Since A3 Submission

### Controller Refactor
* Broke controller up into many different sub-controller, each extending Controller. Package structure now mimics that in view (i.e. there is a loose bijection between Controllers and SceneBuilders). Extensibility greatly improved.

### Room Associations
* Create Room class 
* Add a room field to Device. This is an optional feature that allows the user to group devices together. If there is no associated room set the field to null
* Hub manages the addition and deletion of rooms, and can assist with changing room associations as requested by controllers
* Add storage code to allow storage and retrieval of rooms from separate file, and update storage code for device to hold the UUID of the room associated with a device, if such a room exists.
TODO 
* Write tests to ensure that room mechanics within Model work as expected
* Modify storage code and storage tests
* Add UI components to allow modification of room 