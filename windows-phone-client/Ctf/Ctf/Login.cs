using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Diagnostics;
using System.Threading.Tasks;

namespace Ctf
{
    // User story: https://tracker.blstreamgroup.com/jira/browse/CTFPAT-56
    class Login
    {
        private RequestHandler requestHandler;
        private RestRequest request;
        private string username;

        public Login()
        {
            requestHandler = new RequestHandler();

            request = new RestRequest("/oauth/token", Method.POST);
            request.AddHeader("Content-type", "application/x-www-form-urlencoded");
            request.AddParameter("client_id", "mobile_windows");
            request.AddParameter("grant_type", "password"); 
        }

        
        //* Wprowadzenie złego hasła i loginu powoduje błąd,
        //* Wprowadzenie poprawnych danych tworzy token wymiany którym autoryzowane są wszystkie inne wywołania funkcjonalności.
        // TODO: Error handling
        public void RequestCallbackOnSuccess(IRestResponse<JsonResponse> response)
        {
            if ( (response != null) && (response.Data != null) )
            {
                    if (String.IsNullOrEmpty(response.Data.error))
                    {
                        if (String.IsNullOrEmpty(response.Data.access_token) && String.IsNullOrEmpty(response.Data.token_type) && String.IsNullOrEmpty(response.Data.scope))
                        {
                            Debug.WriteLine("Response Data is NULL or EMPTY.");
                        }
                        else
                        {
                            Debug.WriteLine("Response Data retrieved SUCCESSFULY.");
                            ApplicationSettings.Instance.SaveLoggedUser(new User(username, response.Data.access_token, response.Data.token_type, response.Data.scope));
                        }
                        
                        Debug.WriteLine("response.Data.access_token: " + response.Data.access_token);
                        Debug.WriteLine("response.Data.token_type: " + response.Data.token_type);
                        Debug.WriteLine("response.Data.scope: " + response.Data.scope);
                    }
                    else
                    {
                        Debug.WriteLine("Response Data is an ERROR:");
                        Debug.WriteLine("response.Data.error_code: " + response.Data.error);
                        Debug.WriteLine("response.Data.error_code: " + response.Data.error_description);
                    }
            }
        }

        public void RequestCallbackOnFail(String errorMessage)
        {
            Debug.WriteLine("Exception thrown: " + errorMessage);
            throw new Exception(errorMessage);
        }

        // TODO: Check if is async
        // Issue: https://tracker.blstreamgroup.com/jira/browse/CTFPAT-87
        public async Task<RestRequestAsyncHandle> logInAs(UserCredentials user, string secret)
        {
            if (loggedAs() != null)
                return null; //TODO: throw exception
            if (String.IsNullOrWhiteSpace(secret))
                return null; //TODO: throw exception
            else
                request.AddParameter("client_secret", secret);
            if (user == null)
                return null; //TODO: throw exception
            else
            {
                request.AddParameter("username", user.getUsername());
                request.AddParameter("password", user.getPassword());
                username = user.getUsername();
            }
            return await requestHandler.ExecuteAsync<JsonResponse>(request, RequestCallbackOnSuccess, RequestCallbackOnFail);
        }

        // Issue: https://tracker.blstreamgroup.com/jira/browse/CTFPAT-89
        public User loggedAs()
        {
            return ApplicationSettings.Instance.RetriveLoggedUser();
        }

        // Issue: https://tracker.blstreamgroup.com/jira/browse/CTFPAT-89
        public bool logOut()
        {
            return ApplicationSettings.Instance.removeFromSettings("user");
        }
    }
}
