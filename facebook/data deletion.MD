Politics is a facebook login app and we do not save your personal data in our server. According to Facebook policy, we have to provide User Data Deletion Callback URL or Data Deletion Instructions URL.

If you want to delete your activities for Politics App, you can remove your information by following these steps:

1. Go to your browser of choice.

2. Login directly in our app by going into this [link](https://politicians-fear.herokuapp.com/oauth2/authorization/facebook). This will
ask you to login on facebook again which is necessary for deleting all of your user data in our server.

3. Store the `jwt` field that you got from the previous step. You can store it anywhere you like. 

4. Use an API or HTTP testing tool like [reqbin](https://reqbin.com/) and type in `https://politicians-fear.herokuapp.com` in the 
address bar and just beside the address bar, select `DELETE`. Add an authorization header by clicking on the `Authorization` section 
just below the address bar and select `Bearer Token`. Put in the stored `jwt` token in the `Token` field.

5. Congrats! All of your data from facebook has now been deleted.
