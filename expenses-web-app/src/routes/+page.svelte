<script>
  import Header from '../components/Header.svelte';
  import Footer from '../components/Footer.svelte';
</script>

<Header />
<main class="">
  <!-- Rest of your content goes here -->
  <body class="body">
  <div class="landing-container">
    <h1>Simplify Group Expenses: Your Ultimate Solution for Easy Bill Sharing!</h1>
    <p>Tired of Complex Group Finances? Discover the Easiest Way to Share Bills with Friends! Managing group expenses can be a hassle. Coordinating who owes what, tracking bills, and ensuring everyone pays their fair share can be overwhelming. But worry not, BillShare is here to simplify your group finances.</p>

    <ul>
        <li>Instantly create groups with friends</li>
        <li>Effortlessly track shared bills and expenses</li>
        <li>Automatic calculations for each member's share</li>
        <li>Seamless payment options for settling up</li>
    </ul>

    <p>Why choose BillStare? Save time and avoid awkward money conversations. Enjoy transparency and fairness in group expenses. Stay organized with automated tracking and notifications.</p>

    <p>Ready to simplify your group expenses? Sign up for free today!</p>
</div>
  </body>
</main>
<Footer />
<style lang="scss">
  .body {
      font-family: 'Arial', sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f8f8f8;
      text-align: center;
  }

  .landing-container {
      max-width: 800px;
      margin: 50px auto;
      padding: 20px;
      background-color: #ffffff;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }

  h1 {
      color: #333333;
  }

  p {
      color: #666666;
  }
</style>
