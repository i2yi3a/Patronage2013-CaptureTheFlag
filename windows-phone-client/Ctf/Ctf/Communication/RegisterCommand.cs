using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RestSharp;
using System.Diagnostics;
using System.Reflection;
using Ctf.Communication.DataObjects;
using Ctf.ApplicationTools.DataObjects;
using Ctf.ApplicationTools;

namespace Ctf.Communication
{
    class RegisterCommand : BaseCommand<ServerResponse>
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="Registration"/> class.
        /// </summary>
        public RegisterCommand()
            : base()
        {
            request = new RestRequest("/api/players/add", Method.POST);
            request.AddHeader("Accept", "application/json");
            request.AddHeader("Content-type", "application/json");
            request.RequestFormat = DataFormat.Json;
        }

        /// <summary>
        /// Requests the callback on success.
        /// </summary>
        /// <param name="response">The response.</param>
        private void RequestCallbackOnSuccess(IRestResponse<ServerResponse> response)
        {
            if ((response != null) && (response.Data != null))
            {
                Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response content: " + response.Content));
                Debug.WriteLineIf(response.Data.HasError(), DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response is an Error."));
                Debug.WriteLineIf(!response.Data.HasError(), DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response is a Success."));
                OnRequestFinished(new RequestFinishedEventArgs(response.Data));
            }
        }

        /// <summary>
        /// Requests the callback on fail.
        /// </summary>
        /// <param name="errorMessage">The error message.</param>
        //private void RequestCallbackOnFail(String errorMessage)
        //{
        //    Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Error message: " + errorMessage));
        //    OnRequestFinished(new RequestFinishedEventArgs(new ApplicationError(errorMessage)));
        //}

        public async Task<RestRequestAsyncHandle> RegisterAs(UserCredentials user)
        {
            request.AddBody(new { username = user.username, password = user.password });
            return await ExecuteAsync(request, RequestCallbackOnSuccess, RequestCallbackOnFail);
        }
    }
}
