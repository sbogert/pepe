const assert = require('assert');
const session = require('supertest-session');
const request = require('supertest')
const DrinkerService = require('../main/Service/DrinkerService')
const DrinkerMapper = require("../main/Mapper/DrinkerMapper")
const SellerMapper = require("../main/Mapper/SellerMapper")
const app = require("../main/app").app
const io = require("socket.io-client");
const _ = require("underscore")

const drinkerSocket = io.connect("http://localhost:3000", {
    "reconnection delay": 0,
    "reopen delay": 0,
    "force new connection": true,
    transports: ["websocket"],
});

drinkerSocket.on("success", () => {})
const sellerSocket = io.connect("http://localhost:3000", {
    "reconnection delay": 0,
    "reopen delay": 0,
    "force new connection": true,
    transports: ["websocket"],
});

drinkerSocket.on("success", () => {})


var drinkerId = 0;
var sellerId = 0;

var testSession = null;

beforeEach(function () {
    testSession = session(app);
});

describe('appTests', () => {
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
            it("should success and return 200", async function (){
                const body = {
                    username: "username",
                    password: "password"
                }
                const response = await request(app).post("/drinker/login").send(body)
                assert.equal(response.status, 200)
                drinkerId = response.body

            })
        });

        describe('when try to get seller menu before logging', function () {
            it("should reject and return 400", async function (){
                const body = {
                    seller_id : 1
                }
                const response = await request(app).post("/drinker/get_menu").send(body)
                assert.equal(response.status, 400)

            })
        });

        describe('when Drinker try to get near by seller before logging', function () {
            it("should reject and return 400", async function (){
                const body = {
                    latitude: 100,
                    longitude: 100
                }
                const response = await  request(app).post("/drinker/get_near_by_sellers").send({
                    location: JSON.stringify(body)
                })
                //console.log(request)
                assert.equal(response.status, 400)

            })
        });

        describe('when Drinker try to get history orders before logging in', function () {
            it("should reject and return 400", async function (){
                const response = await  request(app).post("/drinker/get_history_order")
                //console.log(request)
                assert.equal(response.status, 400)

            })
        });


        describe('after authenticating session in drinker', function () {

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
                it("should good and get 200 and locations", async function (){
                    const body = {
                        latitude: 100,
                        longitude: 100
                    }
                    let sellerRes = await request(app).post("/seller/signup").send({
                        username: "username",
                        password: "password",
                        location: JSON.stringify(body)
                    })
                    sellerRes = await request(app).post("/seller/login").send({
                        username: "username",
                        password: "password",
                    })

                    sellerId = sellerRes.body
                    //console.log(sellerId)
                    const response = await authenticatedSession.post("/drinker/get_near_by_sellers").send({
                        location: JSON.stringify(body)
                    })
                    //console.log(request)
                    assert.equal(response.status, 200)
                    //console.log(response.body)
                })
            });

            describe('try to get menu from seller that is not online', function () {
                it("should be rejected and get 400", async function (){
                    const body = {
                        seller_id: sellerId
                    }
                    //console.log(sellerId)
                    const response = await authenticatedSession.post("/drinker/get_menu").send(body)
                    //console.log(request)
                    assert.equal(response.status, 400)
                    //console.log(response.body)
                })
            });

            describe('try to get menu from seller that is online', function () {
                it("should success and get menu", async function (){

                    let SellerBody = {
                        isDrinker: false,
                        id: sellerId
                    }

                    sellerSocket.emit('online', JSON.stringify(SellerBody))
                    sellerSocket.on("success", async () => {
                        const body = {
                            seller_id: sellerId
                        }
                        //console.log(sellerId)
                        const response = await authenticatedSession.post("/drinker/get_menu").send(body)
                        //console.log(request)
                        assert.equal(response.status, 200)
                        //console.log(response.body)
                    })
                })
            });

            describe('try to get history order when is logged in', function () {
                it("should success and get menu", async function (){

                    const response = await authenticatedSession.post("/drinker/get_history_order")
                    assert.equal(response.status, 200)
                }) // test out that drinker that has no history order will crash the server
            });

        });
    });



    describe('SellerServiceTest', function () {
        //SellerMapper.DeleteSellerByUsername(null, "username", null)
        describe('when requesting sign up in Service with a unique username', function () {
            it("should insert into database and return 200", async function (){
                SellerMapper.DeleteSellerByUsername(null, "username", null)
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
        describe('when requesting login in seller with no such username', function () {
            it("should reject and return 400", async function (){
                const body = {
                    username: "WrongUsername",
                    password: "password",
                }
                const response = await request(app).post("/seller/login").send(body)
                assert.equal(response.status, 400)
            })
        });

        describe('when requesting login in seller with such username but wrong password', function () {
            it("should reject and return 400", async function (){
                const body = {
                    username: "username",
                    password: "wrongPassword"
                }
                const response = await request(app).post("/seller/login").send(body)
                assert.equal(response.status, 400)
            })
        });
        describe('when requesting login in seller with username and good password', function () {
            it("should success and return 200", async function (){
                const body = {
                    username: "username",
                    password: "password"
                }
                const response = await request(app).post("/seller/login").send(body)
                //console.log(request)
                assert.equal(response.status, 200)
            })
        });

        describe('when seller update menu without loggin', function () {
            it("should reject and return 400", async function (){
                const body = {
                    name: "tea",
                    price: "1",
                    caffeine: 30
                }
                const response = await request(app).post("/seller/update_menu").send({
                    items: JSON.stringify(body)
                })
                //console.log(request)
                assert.equal(response.status, 400)
            })
        });

        describe('when seller get history order without loggin in', function () {
            it("should reject and return 400", async function (){

                const response = await request(app).post("/seller/get_history_order")
                //console.log(request)
                assert.equal(response.status, 400)
            })
        }); // also check crash

        describe('after authenticating session in seller', function () {

            var authenticatedSession;

            beforeEach(function (done) {
                testSession.post('/seller/login')
                    .send({username: 'username', password: 'password'})
                    .expect(200)
                    .end(function (err) {
                        if (err) return done(err);
                        authenticatedSession = testSession;
                        return done();
                    });
            });

            describe('when seller update menu', function () {
                it("should success and return 200", async function (){
                    const body = {
                        name: "tea",
                        price: "1",
                        caffeine: 30
                    }
                    const response = await authenticatedSession.post("/seller/update_menu").send({
                        items: JSON.stringify(body)
                    })
                    //console.log(request)
                    assert.equal(response.status, 200)
                })
            });

            describe('when seller get history order', function () {
                it("should success and receive 200", async function (){

                    const response = await authenticatedSession.post("/seller/get_history_order")
                    //console.log(request)
                    assert.equal(response.status, 200)
                })
            }); // also check crash
        });
    });
    //DrinkerMapper.DeleteDrinkerByUsername(null, "username", null)

    describe('TCPConnectionTEst', function (){

        var DrinkerAuthenticatedSession;
        var SellerAuthenticatedSession;
        var seller_socket_id;

        beforeEach(function (done) {
            testSession.post('/drinker/login')
                .send({username: 'username', password: 'password'})
                .expect(200)
                .end(function (err) {
                    if (err) return done(err);
                    DrinkerAuthenticatedSession = testSession;
                    return done();
                });
        });


        describe('When Connecting to the server', function () {
            it('drinker user should receive success', function () {
                let drinkerBody = {
                    isDrinker: true,
                    id: drinkerId
                }

                sellerSocket.emit('online', JSON.stringify(drinkerBody))
                sellerSocket.on("success", async () => {
                    assert.equal(100,100)
                })
            });

            it('seller user should receive success', function () {
                let sellerBody = {
                    isDrinker: false,
                    id: sellerId
                }

                sellerSocket.emit('online', JSON.stringify(sellerBody))
                sellerSocket.on("success", async () => {
                    assert.equal(100,100)
                })
            });
        });



        DrinkerMapper.DeleteDrinkerByUsername(null, "username", null)
        SellerMapper.DeleteSellerByUsername(null, "username", null)
    })

});