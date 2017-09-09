var express = require('express');
var router = express.Router();
var MongoClient = require('mongodb').MongoClient;

var app = express();

var url = "mongodb://localhost:27017/Basket";

router.get('/code/:barcode', function(req, res, next) {
	var code = parseInt(req.params.barcode);
	
	MongoClient.connect(url, function(err, db) {
	  if (err) throw err;
	  db.collection("Inventory").find({Barcode: code }, { _id: false}).toArray( function(err, result) {
	    if (err) throw err;

	    console.log(result);
	    res.json(result);
	    res.status(200);
	    res.end();
	    db.close();
	  });
	});
});




router.get('/name/:name', function(req, res, next) {
	var product_name = req.params.name;
	MongoClient.connect(url, function(err, db) {
	  if (err) throw err;
	  db.collection("Inventory").find({"Product Name":{$regex: product_name, $options:"$i" }}, { _id: false}).toArray( function(err, result) {
	    if (err) throw err;
	    console.log(result);

	    if(result.length == 0) {
	    	res.status(202);
	    }
	    else {

	    	var obj = [{"Inventory":result}];
		    var category = result[0].Category;

		    db.collection("Shelf").find({Category: category }, { _id: false}).toArray( function(err, result) {
			    if (err) throw err;
			    console.log(result);
			    obj.push({"Shelf":result});
			    console.log(obj[0].Inventory[0].Category);
			    res.json(obj);
			    db.close();
			    res.status(200);
			    res.end();
			  });
		}
		
	  });
	});
});

module.exports = router;
