<!-- LoginForm.svelte -->
<script>
  import InputField from './InputField.svelte';

  let username = '';
  let password = '';
  let error = '';

  const handleLogin = async () => {
    // Add your login logic here
    console.log('Username:', username);
    console.log('Password:', password);
     try {
      const response = await fetch('YOUR_API_ENDPOINT', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username: username,
          password: password,
        }),
      });

      username = '';
      password = '';
      if (!response.ok) {
        if (response.status === 401) {
          error = 'Invalid username or password';
        } else {
          error = 'An error occurred while logging in';
        }
        return;
      }

      const data = await response.json();
      authToken = data.token; // Assuming the API returns a token
    } catch (error) {
      console.error('Error during login:', error);
    }
  };
</script>

<form>
  <p>
  <InputField label="Username" type="text" bind:value={username} placeholder="Enter your username" />
  <InputField label="Password" type="password" bind:value={password} placeholder="Enter your password" />
  <p>
  <button type="button" on:click={handleLogin}>Login</button>
  {#if error}
    <p style="color: red;">{error}</p>
  {/if}
</form>

<style>
  form {
    max-width: 300px;
    margin: 0 auto;
  }

  button {
    background-color: #4CAF50;
    color: white;
    padding: 10px 15px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    align-items: center;
  }

  button:hover {
    background-color: #45a049;
  }
  </style>

