using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Diagnostics;
using System.Reflection;
using System.Threading.Tasks;
using Ctf.Communication.DataObjects;
using Ctf.ApplicationTools.DataObjects;
using Ctf.ApplicationTools;

namespace Ctf.Communication
{
    /// <summary>
    /// 
    /// </summary>
    class LoginCommand : BaseCommand
    {
        private string username;

        /// <summary>
        /// Initializes a new instance of the <see cref="Login"/> class.
        /// </summary>
        public LoginCommand()
            : base()
        {
            username = String.Empty;
            request = new RestRequest("/oauth/token", Method.POST);
            request.AddHeader("Content-type", "application/x-www-form-urlencoded");
            request.AddParameter("client_id", "mobile_windows");
            request.AddParameter("grant_type", "password");
        }

        /// <summary>
        /// Requests the callback on success.
        /// </summary>
        /// <param name="response">The response.</param>
        public void RequestCallbackOnSuccess(IRestResponse<LoginJsonResponse> response)
        {
            if ((response != null) && (response.Data != null))
            {
                Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response content: " + response.Content));
                if (!response.Data.HasError())
                {
                    ApplicationSettings.Instance.SaveLoggedUser(new User(username, response.Data.access_token, response.Data.token_type, response.Data.scope));
                }
                OnRequestFinished(new RequestFinishedEventArgs(response.Data));
            }
        }

        /// <summary>
        /// Requests the callback on fail.
        /// </summary>
        /// <param name="errorMessage">The error message.</param>
        public void RequestCallbackOnFail(String errorMessage)
        {
            Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Error message: " + errorMessage));
            OnRequestFinished(new RequestFinishedEventArgs(new ApplicationError(errorMessage)));
        }

        //TODO: Check if is async
        /// <summary>
        /// Logs the in as.
        /// </summary>
        /// <param name="user">The user.</param>
        /// <param name="secret">The secret.</param>
        /// <returns></returns>
        public async Task<RestRequestAsyncHandle> LogInAs(UserCredentials user, string secret)
        {
            //Secret could be: System.Guid.NewGuid().ToString()
            //TODO: get method name
            Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, "async Task<RestRequestAsyncHandle> LogInAs(UserCredentials user, string secret)", "Launching request as: \\" + user.username + "\\ pswd: \\" + user.password + "\\ secret: \\" + secret + "\\"));
            request.AddParameter("username", user.username);
            request.AddParameter("password", user.password);
            request.AddParameter("client_secret", secret);
            username = user.username;
            return await requestHandler.ExecuteAsync<LoginJsonResponse>(request, RequestCallbackOnSuccess, RequestCallbackOnFail);
        }

        /// <summary>
        /// Loggeds as.
        /// </summary>
        /// <returns></returns>
        public User LoggedAs()
        {
            return ApplicationSettings.Instance.RetriveLoggedUser();
        }

        /// <summary>
        /// Logs the out.
        /// </summary>
        /// <returns></returns>
        public bool LogOut()
        {
            return ApplicationSettings.Instance.RemoveFromSettings("user");
        }
    }
}
