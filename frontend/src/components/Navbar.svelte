<script>
    import { Navbar, NavBrand, NavLi, NavUl, NavHamburger, Button, Input } from 'flowbite-svelte';
    import Logo from './drxlogo.svg'

</script>

<Navbar let:hidden let:toggle color="form">
    <NavBrand href="/homepage">
      <img
        src={Logo}
        class="mr-3 h-6 sm:h-9"
        alt="Draxlmaier Logo"
      />
      
    </NavBrand>
    <div class="flex md:order-2">
      <Button color = "blue" size="sm">Logout</Button>
      <NavHamburger on:click={toggle} />
    </div>
    <NavHamburger on:click={toggle} />
    <NavUl {hidden}>
      <NavLi href="/addlocations">Locations</NavLi>
      <NavLi href="/drivers">Drivers</NavLi>
      <NavLi href="/makereservation">Make reservation</NavLi>
      <NavLi href="/transports">Transports</NavLi>
    </NavUl>
  </Navbar>

  