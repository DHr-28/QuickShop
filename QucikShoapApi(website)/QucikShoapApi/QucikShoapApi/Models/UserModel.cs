using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace QucikShoapApi.Models
{
    public class UserModel
    {
        public String UserName { get; set; }
        public String Password { get; set; }
    }

    public class UserSignUpModel
    {
        public String Firstname { get; set; }
        public String Lastname { get; set; }
        public String Email { get; set; }
        public String Password { get; set; }
        public String Residency { get; set; }
        public String city { get; set; }
    }

    
    public class OrderCartModel
    {

        public int RetailerUserId { get; set; }
        public String OrderAddress1 { get; set; }
        public String OrderAddress2 { get; set; }
        public String OrderPincode { get; set; }
        public String OrderContactNumber { get; set; }


        public int ProductId { get; set; }
        public int storeUSerid { get; set; }
        public int numberofquantity { get; set; }
        public int OrderId { get; set; }
    }


    public class ResponseModel
    {
        public String status { get; set; }
    }
}