
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("bye", function(request, response) {
  response.success("ByeBye World!");
});

Parse.Cloud.afterSave("update", function(request, response){
	var pushQuery = new Parse.Query(Parse.Installation);
	pushQuery.containedIn("User",Parse.User);

});