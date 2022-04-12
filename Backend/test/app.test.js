const assert = require('assert');
const request = require('supertest')
const DrinkerService = require('../main/Service/DrinkerService')
const DrinkerMapper = require("../main/Mapper/DrinkerMapper")
const app = require("../main/app")

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
                const response = await request(app).post("/drinker/signup").send(body)
                assert.equal(response.status, 400)
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
        describe('when requesting login in drinker with username and good password', function () {
            it("should reject and return 400", async function (){
                const body = {
                    username: "username",
                    password: "password"
                }
                const response = await request(app).post("/drinker/login").send(body)
                console.log(request)
                assert.equal(response.status, 200)

            })
        });
    });
    DrinkerMapper.DeleteDrinkerByUsername(null, "username", null)
});