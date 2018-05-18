using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading;
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;

namespace QucikShoapApi.Models
{
    public class ErrorResult : IHttpActionResult
    {
        private string message;
        private HttpStatusCode statusCode;

        public ErrorResult(string message)
        {
            this.message = message;
            this.statusCode = HttpStatusCode.NotFound;
        }

        public ErrorResult(string message, HttpStatusCode statusCode, Exception e = null)
        {
            this.message = message;
            this.statusCode = statusCode;
            if (e != null)
            {
                //ErrorConsole.SendErrorEmail(e);
            }
        }

        public Task<HttpResponseMessage> ExecuteAsync(CancellationToken cancellationToken)
        {
            var response = new HttpResponseMessage(statusCode);
            response.Content = new StringContent(message);
            return Task.FromResult(response);
        }
    }
}