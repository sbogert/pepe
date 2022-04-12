const assert = require('assert');
const request = require('supertest')
const DrinkerMapper = require("../main/Mapper/DrinkerMapper")
const app = require("../main/app")

/*
describe('MapperTests', () => {
    describe('DrinkerMapperTest', function () {
        describe('when insert int a unique password', function () {
            it("should insert successfully", async function (){
                let body = ["username", "password"]
                try{
                    DrinkerMapper.InsertIntoDrinker(true, body, InsertTest)
                }catch (error){
                    console.log(error)
                }
            })
        });

        describe('when insert int a not unique password', function () {
            it("should fail", async function (){
                let body = ["username", "password"]
                try{
                    DrinkerMapper.InsertIntoDrinker(false, body, InsertTest)
                }catch (error){
                    console.log(error)
                }
            })
        });

    });
    DrinkerMapper.DeleteDrinkerByUsername(null, "username", null)
});

function InsertTest(payload, results){
    //console.log(results)
    if(payload){
        assert.equal(results.affectedRows, 1)
    }else{
        assert.throw(function (error){
            console.log(error)
        })
    }
}*/
