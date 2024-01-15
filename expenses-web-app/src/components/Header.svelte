<script>
  let navOpen = false;

  function toggleMenu() {
    navOpen = !navOpen;
  }
</script>
<header class="header">
  <h1 class="h1">Bill Share</h1>
  <button class="menu-btn" on:click={toggleMenu}>
    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
      <rect x="4" y="7" width="16" height="2" rx="1" fill="#fff" />
      <rect x="4" y="12" width="16" height="2" rx="1" fill="#fff" />
      <rect x="4" y="17" width="16" height="2" rx="1" fill="#fff" />
    </svg>
  </button>
  <div class="nav-bar">
	  <nav>
	    <a href="/">Home</a>
	    <a href="login">Login</a>
	  </nav>
  </div>
</header>

<style lang="scss">
  .header {
    position: relative;
    margin-bottom: 2rem;
    border-radius: 5px;
    overflow: hidden;
    background-color: #4caf50; /* Change this value to your preferred green color */
  }

  .h1 {
    padding: 0.75rem 1rem;
    color: #fff;
    font-size: 1.75rem;
    font-weight: bold;
    text-align: center;
  }

  .menu-btn {
    position: absolute;
    top: 0;
    bottom: 0;
    right: 0;
    padding: 0.75rem 1rem;
    background: none;
    border: none;
    cursor: pointer;
  }
  .nav-bar{
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 50px;
  }
  .nav-bar nav {
    margin-bottom: 20px;
  }

  .nav-bar a {
    margin-right: 10px;
    text-decoration: none;
   color: #fff;
  }
</style>
