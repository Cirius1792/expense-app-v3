<!-- InputField.svelte -->
<script>
  export let label;
  export let type = 'text';
  export let value = '';
  export let placeholder = '';

  const handleChange = (event) => {
    value = event.target.value;
  };
</script>

<div class="input-field">
  <label>{label}</label>
  {#if type === 'text'}
    <input type="text" bind:value={value} placeholder={placeholder} on:input={handleChange} />
  {:else if type === 'password'}
    <input type="password" bind:value={value} placeholder={placeholder} on:input={handleChange} />
  {/if}
</div>

<style>
  .input-field {
    margin-bottom: 20px;
  }

  label {
    display: block;
    margin-bottom: 5px;
  }

  input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
  }
</style>

