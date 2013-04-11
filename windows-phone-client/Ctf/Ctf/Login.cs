using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Diagnostics;

namespace Ctf
{
    // Issue:  https://tracker.blstreamgroup.com/jira/browse/CTFPAT-5
    //        Logowanie
    //Rodzaje klientów (client_id):
    //•mobile_android,
    //•mobile_windows,
    //•mobile_ios,
    //•web_www.
    //Ważne: Każdy klient otrzymuje unikalny token z serwera.
    // About token: http://www.codeproject.com/Articles/75424/Step-by-Step-Guide-to-Delicious-OAuth-API
    class Login
    {
        private RequestHandler requestHandler;

        public Login()
        {
            requestHandler = new RequestHandler(CtfConstants.SERVER_BASE_URL);
            RestRequest r = createRequest();
            makeRequest(r);
        }

        //      Request:
        //URI:
        //  /oauth/token
        //Http method:
        //  POST
        //Headers:
        //  Content-type: application/x-www-form-urlencoded
        //Body:
        //  client_id=mobile_android&client_secret=secret&grant_type=password&username=michal.krawczak@blstream.com&password=ExamPle3retPassw0rd!
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
            request.AddParameter("username", "michal.krawczak@blstream.com");
            request.AddParameter("password", "ExamPle3retPassw0rd!");

            return request;
        }

        public async void makeRequest(RestRequest request)
        {
            await requestHandler.ExecuteAsync<LoginResponse>(request, RequestCallbackOnSuccess, RequestCallbackOnFail);
        }

        //Response (SUCCESS):
        //    Body (JSON):
        //      {
        //        "access_token": "602dd1c4-9d5e-4eda-bbe0-125978c1848e",
        //        "token_type": "bearer",
        //        "scope": "read write"
        //      }
        //Response (FAILED):
        //    Body (JSON):
        //      {
        //        "error": "invalid_grant",
        //        "error_description": "Bad credentials"
        //      }
        public void RequestCallbackOnSuccess(IRestResponse<LoginResponse> response)
        {
            if (response != null)
            {
                if (response.Data != null)
                {
                    Debug.WriteLine("response.Data.access_token: " + response.Data.access_token);
                    Debug.WriteLine("response.Data.token_type: " + response.Data.token_type);
                    Debug.WriteLine("response.Data.scope: " + response.Data.scope);

                    Debug.WriteLine("response.Data.error_code: " + response.Data.error);
                    Debug.WriteLine("response.Data.error_code: " + response.Data.error_description);
                }
            }
        }

        public void RequestCallbackOnFail(String errorMessage)
        {
            Debug.WriteLine(errorMessage);
        }
    }
}
