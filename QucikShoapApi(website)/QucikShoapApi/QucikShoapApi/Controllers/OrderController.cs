using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using QucikShoapApi.Models;

namespace QucikShoapApi.Controllers
{
    
    public class OrderController : ApiController
    {
        [System.Web.Http.HttpPost]
        [System.Web.Http.Route("~/QShoap/OrderCartInsert")]
        public IHttpActionResult OrderCartInsert(OrderCartModel usermodel)
        {
            try
            {
                DbAccess.Order objUser = new DbAccess.Order();

                int orderinsertresult = DbAccess.Order.OrderInsert(usermodel.RetailerUserId, usermodel.OrderAddress1,
                    usermodel.OrderAddress2, usermodel.OrderPincode, usermodel.OrderContactNumber);

                int cartinsertresult = DbAccess.Order.CartInsert(usermodel.ProductId, usermodel.storeUSerid, usermodel.numberofquantity,
                    usermodel.RetailerUserId, orderinsertresult);



                return Ok(new ResponseModel
                {
                    status = "success"
                });

            }
            catch (Exception e)
            {

                return new ErrorResult("Error in request", HttpStatusCode.InternalServerError);
            }
        }


       
    }
}