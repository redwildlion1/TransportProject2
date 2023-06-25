<script>
    import axios from "axios";
    import { goto } from '$app/navigation';
    import { token } from '../routes/auth';
  
    let email = '';
    let password = '';
    let isLoggedIn = false;
    let isRedirecting = false;
  
    async function handleLogin(){
      try{
        const response = await axios.post('http://localhost:8080/api/v1/auth/authenticate', 
        {email, password});
        if(response.status === 200){
          const jwtToken = response.data.token; // Store the received token
          token.set(jwtToken)
          axios.defaults.headers.common['Authorization'] = `Bearer ${jwtToken}`;
          isLoggedIn = true;
          isRedirecting = true;
          goto('homepage');
        }else{
          console.error('Login failed');
        }
      }catch (error){
        console.error('An error occurred during login: ', error);
      }
    }
</script>

{#if !isLoggedIn}
<div class="flex items-center justify-center min-h-screen bg-gray-100">
  <form on:submit|preventDefault={handleLogin} class="bg-white shadow-md rounded-lg px-10 py-8">
    <div class="mb-6">
      <label class="block text-gray-700 text-lg font-semibold mb-2" for="email">
        Email
      </label>
      <input
        class="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
        id="email" type="text" placeholder="Email"
        bind:value={email}
      >
    </div>
    <div class="mb-8">
      <label class="block text-gray-700 text-lg font-semibold mb-2" for="password">
        Password
      </label>
      <input
        class="shadow appearance-none border rounded w-full py-3 px-4 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
        id="password" type="password" placeholder="Password"
        bind:value={password}
      >
    </div>
    <div class="flex items-center justify-center">
      <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-3 px-6 rounded focus:outline-none focus:shadow-outline" type="submit">
        Sign In
      </button>
    </div>
  </form>
</div>
{:else}
  <script>
    if (isRedirecting) {
      goto ('homepage');
    }
  </script>
{/if}