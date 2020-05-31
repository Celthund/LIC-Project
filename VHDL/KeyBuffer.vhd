----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    19:29:45 05/20/2020 
-- Design Name: 
-- Module Name:    KeyBuffer - Behavioral 
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

entity KeyBuffer is
	Port ( 
		CLK : in  STD_LOGIC;
		RST : in  STD_LOGIC;
		DAV : in  STD_LOGIC;
	   ACK : in  STD_LOGIC;
		DAC : out  STD_LOGIC;
	   DVal : out  STD_LOGIC;
		D : in STD_LOGIC_VECTOR (3 downto 0);
	   Q : out STD_LOGIC_VECTOR (3 downto 0)
	 );
end KeyBuffer;

architecture Structural of KeyBuffer is
	COMPONENT Register_D
		GENERIC (
			WIDTH : POSITIVE 
		);
		PORT(
			CLK : IN std_logic;
			RST : IN std_logic;
			D : IN std_logic_vector(WIDTH-1 downto 0);          
			Q : OUT std_logic_vector(WIDTH-1 downto 0)
		);
	END COMPONENT;
	COMPONENT KeyBufferControl is
    Port ( 
			CLK : in  STD_LOGIC;
			RST : in  STD_LOGIC;
			DAV : in  STD_LOGIC;
		   ACK : in  STD_LOGIC;
         DAC : out  STD_LOGIC;
         DVal : out  STD_LOGIC;
         WReg : out  STD_LOGIC
			);
	END COMPONENT;
	Signal Wreg: STD_LOGIC;
begin
	OutputRegister : Register_D 
	GENERIC MAP(
		WIDTH => 4
	)
	PORT MAP(
			CLK => Wreg,
			RST => RST,
			D => D,
			Q => Q
	);
	KeyBufferCtrl : KeyBufferControl
	PORT MAP( 
			CLK => CLK,
			RST => RST,
		   DAV => DAV,
			ACK => ACK,
			DAC => DAC,
			DVal =>DVal,
			WReg => WReg
		);
end Structural;

