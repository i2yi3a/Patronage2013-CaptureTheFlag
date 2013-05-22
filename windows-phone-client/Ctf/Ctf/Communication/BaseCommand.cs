using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RestSharp;
using Ctf.Communication.DataObjects;

namespace Ctf.Communication
{
    public delegate void RequestFinishedEventHandler(object sender, RequestFinishedEventArgs e);

    public class BaseCommand
    {
        protected RequestHandler requestHandler;
        protected RestRequest request;

        public event RequestFinishedEventHandler RequestFinished;

        protected BaseCommand()
        {
            requestHandler = new RequestHandler();
        }

        protected virtual void OnRequestFinished(RequestFinishedEventArgs e)
        {
            var RequestFinishedThreadPrivate = RequestFinished;
            if (RequestFinishedThreadPrivate != null)
                RequestFinishedThreadPrivate(this, e);
        }
    }
}
