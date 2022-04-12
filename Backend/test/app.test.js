const assert = require('assert');
const session = require('supertest-session');
const request = require('supertest')
const DrinkerService = require('../main/Service/DrinkerService')
const DrinkerMapper = require("../main/Mapper/DrinkerMapper")
const app = require("../main/app")

var testSession = null;

beforeEach(function () {
    testSession = session(app);
});

describe('ServiceTests', () => {
    describe('DrinkerServiceTest', function () {
        describe('when requesting sign up in drinker with a unique username', function () {
            it("should insert into database and return 200", async function (){
                const body = {
                    username: "username",
                    password: "password"
                }
                const response = await request(app).post("/drinker/signup").send(body)
                assert.equal(response.status, 200)
            })
        });
        describe('when requesting sign up in drinker with a not unique username', function () {
            it("should reject and return 400", async function (){
                const body = {
                    username: "username",
                    password: "password"
                }
                let response = ""
                try{
                    response = await request(app).post("/drinker/signup").send(body)
                }catch (error){
                    console.log(error)
                    assert.equal(response.status, 400)
                }
            })
        });
        describe('when requesting login in drinker with no such username', function () {
            it("should reject and return 400", async function (){
                const body = {
                    username: "wrongUsername",
                    password: "password"
                }
                const response = await request(app).post("/drinker/login").send(body)
                assert.equal(response.status, 400)
            })
        });
        describe('when requesting login in drinker with such username but wrong password', function () {
            it("should reject and return 400", async function (){
                const body = {
                    username: "username",
                    password: "wrongPassword"
                }
                const response = await request(app).post("/drinker/login").send(body)
                assert.equal(response.status, 400)
            })
        });
        describe('when requesting login in drinker with username and good password', function () {
            it("should reject and return 400", async function (){
                const body = {
                    username: "username",
                    password: "password"
                }
                const response = await request(app).post("/drinker/login").send(body)
                assert.equal(response.status, 200)

            })
        });

        /*describe('after authenticating session in drinker', function () {

            var authenticatedSession;

            beforeEach(function (done) {
                testSession.post('/drinker/login')
                    .send({ username: 'username', password: 'password' })
                    .expect(200)
                    .end(function (err) {
                        if (err) return done(err);
                        authenticatedSession = testSession;
                        return done();
                    });
            });

            describe('try to get near by seller', function () {
                it("should reject and return 400", async function (){
                    const body = {
                        latitude: 100,
                        longitude: 100
                    }
                    let sellerRes = await request(app).post("/seller/signup").send({
                        username: "username",
                        password: "password",
                        location: JSON.stringify(body)
                    })
                    const response = await request(app).post("/drinker/get_near_by_sellers").send(body)
                    //console.log(request)
                    assert.equal(response.status, 200)
                    DrinkerMapper.DeleteDrinkerByUsername(null, "username", null)
                })
            });

        });*/
    });



    describe('SellerServiceTest', function () {
        describe('when requesting sign up in Service with a unique username', function () {
            it("should insert into database and return 200", async function (){
                const body = {
                    username: "username",
                    password: "password",
                    location: JSON.stringify({
                        latitude: 100,
                        longitude: 100
                    })
                }
                const response = await request(app).post("/seller/signup").send(body)
                assert.equal(response.status, 200)
            })
        });
        describe('when requesting sign up in Service with a not unique username', function () {
            it("should reject and return 400", async function (){
                const body = {
                    username: "username",
                    password: "password",
                    location: JSON.stringify({
                        latitude: 100,
                        longitude: 100
                    })
                }
                let response = ""
                try{
                    response = await request(app).post("/seller/signup").send(body)
                }catch (error){
                    console.log(error)
                    assert.equal(response.status, 400)
                }
            })
        });
        describe('when requesting login in drinker with no such username', function () {
            it("should reject and return 400", async function (){
                const body = {
                    username: "WrongUsername",
                    password: "password",
                }
                const response = await request(app).post("/seller/login").send(body)
                assert.equal(response.status, 400)
            })
        });

        describe('when requesting login in drinker with such username but wrong password', function () {
            it("should reject and return 400", async function (){
                const body = {
                    username: "username",
                    password: "wrongPassword"
                }
                const response = await request(app).post("/seller/login").send(body)
                assert.equal(response.status, 400)
            })
        });
        describe('when requesting login in drinker with username and good password', function () {
            it("should reject and return 400", async function (){
                const body = {
                    username: "username",
                    password: "password"
                }
                const response = await request(app).post("/seller/login").send(body)
                assert.equal(response.status, 200)

            })
        });
        describe('when requesting login in drinker with username and good password', function () {
            it("should reject and return 400", async function (){
                const body = {
                    username: "username",
                    password: "password"
                }
                const response = await request(app).post("/seller/login").send(body)
                //console.log(request)
                assert.equal(response.status, 200)
                DrinkerMapper.DeleteDrinkerByUsername(null, "username", null)
            })
        });
    });
    //DrinkerMapper.DeleteDrinkerByUsername(null, "username", null)
});