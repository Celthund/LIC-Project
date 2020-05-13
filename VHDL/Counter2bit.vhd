----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    19:38:04 05/13/2020 
-- Design Name: 
-- Module Name:    Counter2bit - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity Counter2bit is
    Port ( CE : in  STD_LOGIC;
           CLK : in  STD_LOGIC;
           RST : in  STD_LOGIC;
           Q : buffer STD_LOGIC_VECTOR (1 downto 0));
end Counter2bit;

architecture Structural of Counter2bit is
	COMPONENT Register_T
		GENERIC (
			WIDTH : POSITIVE 
		);
		PORT(
			CLK : IN std_logic;
			RST : IN std_logic;
			T : IN std_logic_vector(WIDTH-1 downto 0);          
			Q : buffer std_logic_vector(WIDTH-1 downto 0)
		);
	END COMPONENT;

begin
	U0 : Register_T 
	GENERIC MAP(
		WIDTH => 1
	)
	PORT MAP(
			CLK => CLK,
			RST => RST,
			T(0) =>  CE,
			Q(0) => Q(0)
	);
	U1 : Register_T 
	GENERIC MAP(
		WIDTH => 1
	)
	PORT MAP(
			CLK => CLK,
			RST => RST,
			T(0) => CE and Q(0),
			Q(0) => Q(1)
	);
end Structural;

