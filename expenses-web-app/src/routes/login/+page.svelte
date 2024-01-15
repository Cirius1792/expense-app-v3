<!-- App.svelte -->
<script>
  import Header from '../../components/Header.svelte';
  import Footer from '../../components/Footer.svelte';
  import LoginForm from '../../components/LoginForm.svelte';
</script>

<Header />
<main>
  <h1>Login</h1>
  <LoginForm />
</main>
<Footer />
<style>
  main {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 50px;
  }

  h1 {
    color: #333;
  }

  nav {
    margin-bottom: 20px;
  }

  a {
    margin-right: 10px;
    text-decoration: none;
    color: #333;
  }
</style>

