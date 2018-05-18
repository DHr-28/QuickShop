using QucikShoapApi.DbAccess;
using QucikShoapApi.Models;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Http;
using System.Web.Mvc;

namespace QucikShoapApi.Controllers
{
    [System.Web.Http.RoutePrefix("QShoapApp")]
    public class UserController : ApiController
    {
        //http://localhost:54433/QShoap/Login
        [System.Web.Http.HttpPost]
        [System.Web.Http.Route("~/QShoap/Login")]
        public IHttpActionResult Login(UserModel usermodel)
        {
            try
            {
                User objUser = new DbAccess.User();

                System.Data.DataSet ds = DbAccess.User.UserLoginCheck(usermodel.UserName, usermodel.Password);
                int rowcount = ds.Tables[0].Rows.Count;
                if (rowcount > 0)
                {
                    var lst = ds.Tables[0].AsEnumerable().Select(p => new
                    {
                        Userid = p.Field<int>("Userid"),
                        Firstname = p.Field<string>("Firstname"),
                        Email = p.Field<string>("Email"),
                        Password = p.Field<string>("Password")
                    }).ToList();
                    return Ok(new
                    {
                        lstdata = lst
                    });
                }
                else
                {
                    var lst = ds.Tables[0].AsEnumerable().Select(p => new
                    {
                        Userid = 0,
                        Firstname = "",
                        Email = "",
                        Password = ""
                    }).ToList();
                    return Ok(new
                    {
                        lstdata = lst
                    });
                }

            }
            catch (Exception e)
            {

                return new ErrorResult("Error in request", HttpStatusCode.InternalServerError);
            }
        }


        [System.Web.Http.HttpPost]
        [System.Web.Http.Route("~/QShoap/SignUp")]
        public IHttpActionResult SignUp(UserSignUpModel usermodel)
        {
            try
            {
                User objUser = new DbAccess.User();

                int UserSignUpresult = DbAccess.User.UserSignUp(usermodel.Firstname, usermodel.Lastname, usermodel.Email, usermodel.Password, usermodel.Residency, usermodel.city);
                return Ok(new ResponseModel
                {
                    status = UserSignUpresult.ToString()
                });

            }
            catch (Exception e)
            {

                return new ErrorResult("Error in request", HttpStatusCode.InternalServerError);
            }
        }

    }
}