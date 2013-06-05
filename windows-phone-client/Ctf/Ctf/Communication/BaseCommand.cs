using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RestSharp;
using Ctf.Communication.DataObjects;
using Ctf.ApplicationTools.DataObjects;
using System.Reflection;
using Ctf.ApplicationTools;
using System.Diagnostics;
using System.Threading;

namespace Ctf.Communication
{
    public delegate void RequestFinishedEventHandler(object sender, RequestFinishedEventArgs e);

    public class BaseCommand<T> where T : ResponseBase, new()
    {
        private string CAPTURE_THE_FLAG_URL = "http://capturetheflag.blstream.com:18080/demo";
        protected RestClient client;
        protected RestRequest request;
        
        public event RequestFinishedEventHandler RequestFinished;

        protected BaseCommand()
        {
            client = new RestClient(CAPTURE_THE_FLAG_URL);
        }

        protected virtual async Task<RestRequestAsyncHandle> ExecuteAsync(RestRequest request, Action<IRestResponse<T>> CallbackOnSuccess, Action<String> CallbackOnFail)
        {

            RestRequestAsyncHandle asyncHandler = client.ExecuteAsync<T>(request, (response) =>
            {
                if (response != null && response.ResponseStatus == ResponseStatus.Error)
                {
                    CallbackOnFail(response.ErrorMessage);
                }
                else
                {
                    CallbackOnSuccess(response);
                }
            });
            return asyncHandler;
        }

        protected virtual void RequestCallbackOnSuccess(IRestResponse<T> response)
        {
            if ((response != null) && (response.Data != null))
            {
                Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response content: " + response.Content));
                OnRequestFinished(new RequestFinishedEventArgs(response.Data));
            }
        }

        protected virtual void RequestCallbackOnFail(String errorMessage)
        {
            Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Error message: " + errorMessage));
            OnRequestFinished(new RequestFinishedEventArgs(new ApplicationError(errorMessage)));
        }

        protected virtual void OnRequestFinished(RequestFinishedEventArgs e)
        {
            var RequestFinishedThreadPrivate = RequestFinished;
            if (RequestFinishedThreadPrivate != null)
                RequestFinishedThreadPrivate(this, e);
        }
    }
}
