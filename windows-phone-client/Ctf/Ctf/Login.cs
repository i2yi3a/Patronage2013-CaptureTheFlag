using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Diagnostics;

namespace Ctf
{
    // User story: https://tracker.blstreamgroup.com/jira/browse/CTFPAT-56
    class Login
    {
        private RequestHandler requestHandler;

        public Login()
        {
            requestHandler = new RequestHandler(CtfConstants.SERVER_BASE_URL);
        }

        //client_secret=secret
        //Aktualnie dla celów deweloperskich jego wartość wynosi secret. Ważne jest aby ten parametr dla każdego z klientów był konfigurowalny.
        public RestRequest createRequest()
        {
            // TODO: Handle exception when incorrect URI: An exception of type 'System.Xml.XmlException' occurred in System.Xml.ni.dll and wasn't handled before a managed/native boundary
            // TODO: Handle two exceptionsalways occuring: An exception of type 'System.Net.WebException' occurred in System.Windows.ni.dll and wasn't handled before a managed/native boundary
            // TODO: Consider Uri with proper UriKind instead of String
            RestRequest request = new RestRequest("/oauth/token", Method.POST);

            request.AddHeader("Content-type", "application/x-www-form-urlencoded");

            request.AddParameter("client_id", CtfConstants.CLIENT_ID);
            request.AddParameter("client_secret", "secret");
            request.AddParameter("grant_type", "password");
            request.AddParameter("username", "piotrekm44@o2.pl");
            request.AddParameter("password", "weakPassword");

            return request;
        }

        public async void makeRequest(RestRequest request)
        {
            await requestHandler.ExecuteAsync<LoginResponse>(request, RequestCallbackOnSuccess, RequestCallbackOnFail);
        }


        //* Wprowadzenie poprawnych danych tworzy token wymiany którym autoryzowane są wszystkie inne wywołania funkcjonalności.
        public void RequestCallbackOnSuccess(IRestResponse<LoginResponse> response)
        {
            if (response != null)
            {
                if (response.Data != null)
                {
                    Debug.WriteLine("Response Data:");
                    Debug.WriteLine("response.Data.access_token: " + response.Data.access_token);
                    Debug.WriteLine("response.Data.token_type: " + response.Data.token_type);
                    Debug.WriteLine("response.Data.scope: " + response.Data.scope);

                    Debug.WriteLine("response.Data.error_code: " + response.Data.error);
                    Debug.WriteLine("response.Data.error_code: " + response.Data.error_description);


                    ApplicationSettings s = ApplicationSettings.Instance;
                    s.SaveLoginSession(response.Data);

                    LoginResponse r = s.RetriveLoginSession();

                    Debug.WriteLine("In ApplicationSettings Right after saved:");
                    if (r != null)
                    {
                        Debug.WriteLine("response.Data.access_token: " + r.access_token);
                        Debug.WriteLine("response.Data.token_type: " + r.token_type);
                        Debug.WriteLine("response.Data.scope: " + r.scope);

                        Debug.WriteLine("response.Data.error_code: " + r.error);
                        Debug.WriteLine("response.Data.error_description: " + r.error_description);
                    }
                    else
                    {
                        Debug.WriteLine("r == NULL");
                    }
                }
            }
        }

        public void RequestCallbackOnFail(String errorMessage)
        {
            Debug.WriteLine(errorMessage);
        }


        //* Logowanie odbywa się w oparciu o login i hasło,
        //* Wprowadzenie złego hasła i loginu powoduje błąd,
        // Issue: https://tracker.blstreamgroup.com/jira/browse/CTFPAT-87
        public void logIn(string login, string password)
        {

        }

        // Issue: https://tracker.blstreamgroup.com/jira/browse/CTFPAT-89
        public string getUsername()
        {
            return "";
        }

        // Issue: https://tracker.blstreamgroup.com/jira/browse/CTFPAT-89
        public void logOut()
        {

        }
    }
}
